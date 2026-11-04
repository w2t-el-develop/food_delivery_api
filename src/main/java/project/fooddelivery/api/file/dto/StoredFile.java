package project.fooddelivery.api.file.dto;

import java.time.LocalTime;

public record StoredFile(
        String key,
        String originalName,
        String contentType,
        Long size,
        String eTag,
        LocalTime lastModified
) {
}
