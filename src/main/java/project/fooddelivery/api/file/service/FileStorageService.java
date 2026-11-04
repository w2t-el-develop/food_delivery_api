package project.fooddelivery.api.file.service;

import project.fooddelivery.api.file.dto.StoredFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public interface FileStorageService {
    StoredFile upload(String prefix, String originalName, String contentType, Long size, InputStream content);
    ResponseInputStream<GetObjectResponse> download(String key);
    boolean exists(String key);
    void delete(String key);
    List<StoredFile> list(String prefix);
    URL presignedDownloadUrl(String key, Duration ttl);
}
