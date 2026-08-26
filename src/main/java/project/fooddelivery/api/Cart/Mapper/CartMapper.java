package project.fooddelivery.api.Cart.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import project.fooddelivery.api.Cart.dto.CartRequestDto;
import project.fooddelivery.api.Cart.model.Cart;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    CartRequestDto toCartRequestDto(Cart cart) ;
}
