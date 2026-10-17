package project.fooddelivery.api.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.cart.dto.AddToCartRequestDto;
import project.fooddelivery.api.cart.dto.CartResponseDto;
import project.fooddelivery.api.cart.entity.Cart;
import project.fooddelivery.api.cart.entity.CartItem;
import project.fooddelivery.api.cart.repository.CartItemRepository;
import project.fooddelivery.api.cart.repository.CartRepository;
import project.fooddelivery.api.menu.entity.MenuItem;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.menu.service.MenuItemService;

import java.util.Optional;
import java.util.UUID;

@Primary
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemService cartItemService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartHelperService cartHelperService;
    private final MenuItemService menuItemService;

    public CartResponseDto viewCart(UUID customerId) {
        Cart cart = getCartByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return CartResponseDto.buildCartResponseDto(cart.getCartId(), cartItemService.getCartItems(cart.getCartId()));
    }


    @Transactional
    public CartItem addToCart(UUID customerId, AddToCartRequestDto addToCartRequestDto) {
        MenuItem menuItem = menuItemService.getMenuItemIfAvailable(addToCartRequestDto.menItemId());
        Cart cart = getCartByCustomerId(customerId).orElseGet(() -> createCart(customerId));
        return cartItemService.createCartItem(cart, menuItem, addToCartRequestDto.quantity());
    }


    public Optional<Cart> getCartByCustomerId(UUID customerId) {
        return cartRepository.getCartByCustomerId(customerId);
    }


    private Cart createCart(UUID customerId) {
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        return cartRepository.save(cart);
    }

    
    @Transactional
    public void clearCart(UUID customerId) {
        cartHelperService.isCartBelongsToCustomer(customerId);
        cartItemRepository.deleteAllByCustomerId(customerId);
    }

}
