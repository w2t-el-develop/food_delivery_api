package project.fooddelivery.api.file.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.fooddelivery.api.configuration.seaweedfs.SeaweedProperties;
import project.fooddelivery.api.file.dto.StoredFile;
import project.fooddelivery.api.file.exception.FileNotFoundInStorageException;
import project.fooddelivery.api.file.exception.StorageException;
import project.fooddelivery.api.file.service.FileStorageService;
import project.fooddelivery.api.utility.TimeUtility;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    private final S3Client s3Client;
    private final S3Presigner s3presigner;
    private final SeaweedProperties seaweedProperties;

    @EventListener(ApplicationReadyEvent.class)
    void ensureBucket() {
        if (!seaweedProperties.createBucketOnStartup()) return;
        try {
            s3Client.headBucket(bucketBuilder -> bucketBuilder.bucket(seaweedProperties.bucket()));
        } catch (NoSuchBucketException ex) {
            s3Client.createBucket(bucketBuilder -> bucketBuilder.bucket(seaweedProperties.bucket()));
        }
    }

    @Override
    public StoredFile upload(String prefix, String originalName, String contentType, Long size, InputStream content) {
        String key = buildKey(prefix, originalName);
        try {
            PutObjectResponse response = s3Client.putObject(objectBuilder -> objectBuilder
                            .bucket(seaweedProperties.bucket())
                            .key(key)
                            .contentType(contentType)
                            .contentLength(size)
                            .metadata(Map.of("original-name", safe(originalName))),
                    RequestBody.fromInputStream(content, size));
            return new StoredFile(key, originalName, contentType, size, response.eTag(), LocalTime.now());
        } catch (SdkException ex) {
            throw new StorageException("Upload failed for: " + key + "because: " + ex.toString());
        }
    }

    @Override
    public ResponseInputStream<GetObjectResponse> download(String key) {
        try {
            return s3Client.getObject(objectBuilder -> objectBuilder.bucket(seaweedProperties.bucket()).key(key));
        } catch (NoSuchKeyException e) {
            throw new FileNotFoundInStorageException("No file found with key: " + key);
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            s3Client.headObject(objectBuilder -> objectBuilder.bucket(seaweedProperties.bucket()).key(key));
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) return false;
            throw new StorageException("HEAD failed for: " + key + "because: " + e.toString());
        }
    }

    @Override
    public void delete(String key) {
        s3Client.deleteObject(objectBuilder -> objectBuilder.bucket(seaweedProperties.bucket()).key(key));
    }

    @Override
    public List<StoredFile> list(String prefix) {
        return s3Client.listObjectsV2Paginator(objectPaginator -> objectPaginator.bucket(seaweedProperties.bucket()).prefix(prefix))
                .contents().stream()
                .map(s3Object -> new StoredFile(s3Object.key(),
                        null, null,
                        s3Object.size(), s3Object.eTag(), TimeUtility.convertFromInstantToLocalTime(s3Object.lastModified())))
                .toList();
    }

    @Override
    public URL presignedDownloadUrl(String key, Duration ttl) {
        return s3presigner.presignGetObject(presingObjectBuilder -> presingObjectBuilder
                        .signatureDuration(ttl)
                        .getObjectRequest(objectRequestBuilder -> objectRequestBuilder.bucket(seaweedProperties.bucket()).key(key)))
                .url();
    }

    private static String buildKey(String prefix, String originalName) {
        LocalDate d = LocalDate.now();
        String ext = StringUtils.getFilenameExtension(originalName);
        return "%s/%d/%02d/%s%s".formatted(prefix, d.getYear(), d.getMonthValue(),
                UUID.randomUUID(), ext == null ? "" : "." + ext.toLowerCase());
    }

    private static String safe(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }
}
