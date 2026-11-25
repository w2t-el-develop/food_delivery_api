package project.fooddelivery.api.image.dto;

import java.time.LocalTime;

public record StoredImage(
        String key,
        String originalName,
        String contentType,
        Long size,
        String eTag,
        LocalTime lastModified
) {
}
