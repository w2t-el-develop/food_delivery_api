package project.fooddelivery.api.core.cart.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.core.cart.dto.*;
import project.fooddelivery.api.core.cart.entity.Cart;
import project.fooddelivery.api.core.cart.entity.CartItem;
import project.fooddelivery.api.core.cart.repository.CartItemRepository;
import project.fooddelivery.api.core.cart.repository.CartRepository;
import project.fooddelivery.api.core.menu.MockMenuItem;
import project.fooddelivery.api.core.menu.MockMenuItemRepository;
import project.fooddelivery.api.core.menu.exception.MenuItemExistenceException;
import project.fooddelivery.api.utility.exceptionhandling.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Primary
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemService cartItemService;
    private final CartRepository cartRepository;
    private final MockMenuItemRepository mockMenuItemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartValidationService cartValidationService;

    public CartResponseDto viewCart(UUID customerId) {
        Cart cart = getCartByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return CartResponseDto.buildCartResponseDto(cart.getCartId(), cartItemService.getCartItems(cart.getCartId()));
    }


    @Transactional
    public CartItem addToCart(UUID customerId, AddToCartRequestDto addToCartRequestDto) {
        MockMenuItem menuItem = mockMenuItemRepository.getById(addToCartRequestDto.menItemId())
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found"));
        if (!menuItem.available()) {
            //todo: move menu item validation to menu item service.
            throw new MenuItemExistenceException("MenuItem is not available");
        }
        Cart cart = getCartByCustomerId(customerId).orElseGet(() -> createCart(customerId));
        return cartItemService.createCartItem(cart, menuItem, addToCartRequestDto.quantity());
    }


    Optional<Cart> getCartByCustomerId(UUID customerId) {
        return cartRepository.getCartByCustomerId(customerId);
    }


    private Cart createCart(UUID customerId) {
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        return cartRepository.save(cart);
    }

    
    @Transactional
    public void clearCart(UUID customerId) {
        cartValidationService.isCartBelongsToCustomer(customerId);
        cartItemRepository.deleteAllByCustomerId(customerId);
    }

}
