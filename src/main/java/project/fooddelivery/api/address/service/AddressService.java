package project.fooddelivery.api.address.service;

import com.example.food_Delivery_api_v3.address.Dto.AddToAddressRequestDto;
import com.example.food_Delivery_api_v3.address.Repositry.AddressRepository;
import com.example.food_Delivery_api_v3.address.entity.Address;
import com.example.food_Delivery_api_v3.customer.MockCustomerRepository;
import com.example.food_Delivery_api_v3.exceptionHandling.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MockCustomerRepository mockCustomerRepository;

    private void isAddressBelongCustomer(UUID customerId) {
        boolean result = mockCustomerRepository.isExistCustomer(customerId);
        if (!result) {
            throw new ResourceNotFoundException("Customer Not Found");
        }
    }


    public Address addAddress(UUID customerId, AddToAddressRequestDto addToAddressRequestDto) {
        isAddressBelongCustomer(customerId);
        Address address = Address.builder()
                .customerId(customerId)
                .phoneNumber(addToAddressRequestDto.PhoneNumber())
                .city(addToAddressRequestDto.city())
                .street(addToAddressRequestDto.street())
                .apartmentNumber(String.valueOf(addToAddressRequestDto.apartmentNumber()))
                .buildingNumber(String.valueOf(addToAddressRequestDto.buildingNumber()))
                .build();
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public void deleteAddress(UUID customerId, UUID addressId) {
        isAddressBelongCustomer(customerId);
        isAddressBelongCustomerId(customerId, addressId);
        Integer deletedAddress = addressRepository.deleteByAddressIdAndCustomerId(addressId, customerId);
        if (deletedAddress == 0) throw new ResourceNotFoundException("Address Not Found");
    }

    private void isAddressBelongCustomerId(UUID customerId, UUID addressId) {
        boolean result = addressRepository.existsAddressByAddressIdAndCustomerId(addressId, customerId);
        if (!result) {
            throw new ResourceNotFoundException("Address Not Found");
        }

    }
}
