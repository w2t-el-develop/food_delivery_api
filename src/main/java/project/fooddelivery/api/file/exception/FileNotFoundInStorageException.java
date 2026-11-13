package project.fooddelivery.api.file.exception;

public class FileNotFoundInStorageException extends RuntimeException{
    public FileNotFoundInStorageException(String message) {
        super(message);
    }
}
