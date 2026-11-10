package project.fooddelivery.api.image.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.exception.BadRequestException;
import project.fooddelivery.api.image.service.ImageChecker;


@Component("ImageContentChecker")
public class ImageContentChecker implements ImageChecker {

    @Override
    public void check(MultipartFile image) {
        String name = image.getOriginalFilename();
        if(image.isEmpty()){
            throw new BadRequestException(ErrorMessage.FILE_IS_EMPTY.getMessage() +" "+ name);
        }
    }
}
