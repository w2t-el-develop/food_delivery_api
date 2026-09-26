package project.fooddelivery.api.address.mapper;

import com.example.food_Delivery_api_v3.address.Dto.AddressResponseDto;
import com.example.food_Delivery_api_v3.address.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    AddressResponseDto toResponseDto(Address address);
}
