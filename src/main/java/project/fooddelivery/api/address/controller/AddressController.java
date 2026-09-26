package project.fooddelivery.api.address.controller;

import com.example.food_Delivery_api_v3.address.Dto.AddToAddressRequestDto;
import com.example.food_Delivery_api_v3.address.Dto.AddressResponseDto;
import com.example.food_Delivery_api_v3.address.mapper.AddressMapper;
import com.example.food_Delivery_api_v3.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/{customerId}/address")
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(@PathVariable UUID customerId, @Valid @RequestBody AddToAddressRequestDto addToAddressRequestDto) {
        AddressResponseDto addressDto = addressMapper.toResponseDto(addressService.addAddress(customerId, addToAddressRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDto);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID customerId, @PathVariable UUID addressId) {
        addressService.deleteAddress(customerId, addressId);
        return ResponseEntity.noContent().build();
    }

}
