package project.fooddelivery.api.image.service;

import org.springframework.web.multipart.MultipartFile;
import project.fooddelivery.api.image.enums.ImageType;

public interface ImageTypeDetector {
    public ImageType detect(MultipartFile image);
}
