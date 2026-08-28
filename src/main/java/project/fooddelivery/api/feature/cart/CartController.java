package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("customer/{customerId}")
    public ResponseEntity<CartWithItemsResponseDTO> getCartByCustomerId(@PathVariable UUID customerId) {
        return new ResponseEntity<>(cartService.getCartByCustomerId(customerId), HttpStatus.OK);
    }

}
