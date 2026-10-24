package project.fooddelivery.api.auth.dto;

import lombok.Builder;

@Builder
public record TokenResponseDto(String accessToken, String refreshToken, String tokenType, long expiresIn) {
}
