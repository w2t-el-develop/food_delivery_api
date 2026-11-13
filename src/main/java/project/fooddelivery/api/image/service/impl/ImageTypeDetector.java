package project.fooddelivery.api.image.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.exception.BadRequestException;
import project.fooddelivery.api.image.enums.ImageType;
import project.fooddelivery.api.image.service.ImageChecker;

import java.io.IOException;
import java.io.InputStream;


@RequiredArgsConstructor
@Component
public class ImageTypeDetector implements project.fooddelivery.api.image.service.ImageTypeDetector {

    private final Tika tika = new Tika();

    @Qualifier("ImageContentChecker")
    private final ImageChecker imageContentChecker;

    @Qualifier("ImageSizeChecker")
    private final ImageChecker imageSizeChecker;

    @Override
    public ImageType detect(MultipartFile image) {
        String imageName = image.getOriginalFilename();
        imageContentChecker.check(image);
        imageSizeChecker.check(image);
        String detectedImageType;
        try (InputStream in = image.getInputStream()) {
            detectedImageType = tika.detect(in, imageName);
        } catch (IOException ex) {
            throw new BadRequestException(ErrorMessage.FILE_COULD_NOT_BE_READ.getMessage() + " " + imageName);
        }
        return ImageType.fromMimeType(detectedImageType)
                .orElseThrow(() -> new BadRequestException("Only JPEG, PNG or WebP images are allowed: " + imageName + " is " + detectedImageType));
    }


}
