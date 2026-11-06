package project.fooddelivery.api.image.enums;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public enum ImageType {
    JPEG("image/jpeg", "jpg"),
    PNG("image/png", "png");

    private final String mimeType;
    private final String extension;

    ImageType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public String mimeType()  { return mimeType; }
    public String extension() { return extension; }


    public static Optional<ImageType> fromMimeType(String mimeType){
        if(mimeType == null) return Optional.empty();
        String normalized = mimeType.toLowerCase(Locale.ROOT).split(";")[0].trim();
        return Arrays.stream(values())
                .filter(imageType -> imageType.mimeType.equals(normalized))
                .findFirst();
    }
}
