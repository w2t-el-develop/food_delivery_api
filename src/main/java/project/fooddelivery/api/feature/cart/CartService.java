package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class CartService {

    private final CartRepository cartRepository;

//    Cart getCartByCustomerId(String customerId){
//
//    }

    public List<Cart> getAllCart(){
        return cartRepository.findAll()
                ;
    }

    public Optional<Cart> getCartByCustomerId(UUID CustomerID){
        return cartRepository.findCartByCustomerId(CustomerID);
    }

}
