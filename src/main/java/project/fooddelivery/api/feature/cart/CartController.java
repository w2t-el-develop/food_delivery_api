package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;



}
