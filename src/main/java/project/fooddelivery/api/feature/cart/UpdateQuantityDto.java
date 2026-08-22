package project.fooddelivery.api.feature.cart;


import lombok.Builder;

@Builder
record UpdateQuantityDto(int quantity) {
}
