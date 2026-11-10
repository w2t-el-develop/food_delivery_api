package project.fooddelivery.api.image.service;


import org.springframework.web.multipart.MultipartFile;

public interface ImageChecker {
    void check(MultipartFile image);
}
