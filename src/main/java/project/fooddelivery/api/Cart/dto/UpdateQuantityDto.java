package project.fooddelivery.api.Cart.dto;

import lombok.Builder;

@Builder
public record UpdateQuantityDto(int newQuantity) {
}
