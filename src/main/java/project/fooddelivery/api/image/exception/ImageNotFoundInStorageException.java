package project.fooddelivery.api.image.exception;

public class ImageNotFoundInStorageException extends RuntimeException{
    public ImageNotFoundInStorageException(String message) {
        super(message);
    }
}
