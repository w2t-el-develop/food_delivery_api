package project.fooddelivery.api.image.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import project.fooddelivery.api.exception.BadRequestException;
import project.fooddelivery.api.image.service.ImageChecker;

@Component("ImageSizeChecker")
public class ImageSizeChecker implements ImageChecker {

    static final DataSize MAX_IMAGE_SIZE = DataSize.ofMegabytes(5);

    @Override
    public void check(MultipartFile image) {
        if(image.getSize() > MAX_IMAGE_SIZE.toBytes()){
            throw new BadRequestException("Image larger than " + MAX_IMAGE_SIZE.toMegabytes() + " MB");
        }
    }
}
