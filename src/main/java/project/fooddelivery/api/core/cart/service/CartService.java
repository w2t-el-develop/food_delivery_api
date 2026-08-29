package project.fooddelivery.api.core.cart.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.core.cart.dto.AddToCartRequestDto;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.core.cart.dto.CartResponseDto;
import project.fooddelivery.api.core.cart.mapper.CartMapper;
import project.fooddelivery.api.core.cart.model.Cart;
import project.fooddelivery.api.core.cart.model.CartItem;
import project.fooddelivery.api.core.cart.repository.CartItemRepository;
import project.fooddelivery.api.core.cart.repository.CartRepository;
import project.fooddelivery.api.core.menu.MockMenuItem;
import project.fooddelivery.api.core.menu.MockMenuItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MockMenuItemRepository mockMenuItemRepository;
    private final CartMapper cartMapper;

    public CartResponseDto viewCart(UUID customerId) {
        Cart cart = getCart(customerId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        UUID cartId = cart.getCartId();
        return CartResponseDto.builder()
                .cartId(cartId)
                .cartItems(getCartItems(cartId))
                .build();
    }

    Optional<Cart> getCart(UUID customerId) {
        return cartRepository.getCartByCustomerId(customerId);
    }

    private List<CartItemResponseDto> getCartItems(UUID CartID) {
        return cartItemRepository.getCartItemsByCart_CartId(CartID);
    }

    @Transactional
    public CartItemResponseDto addToCart(UUID customerId, AddToCartRequestDto addToCartRequestDto) {
        MockMenuItem menuItem = mockMenuItemRepository
                .getById(addToCartRequestDto.menItemId())
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found"));

        if (!menuItem.available()) {
            throw new ValidationException("MenuItem is not available");
        }

        Cart cart = getCart(customerId).orElseGet(() -> createCart(customerId));

        CartItem cartItem = cartItemRepository
                .getCartItemByCart_cartIdAndMenuItemId(
                        cart.getCartId(),
                        menuItem.menuItemId()
                )
                .map(existingItem -> {
                    incrementQuantity(existingItem, addToCartRequestDto.quantity());
                    return existingItem;
                })
                .orElseGet(() ->
                        createCartItem(
                                cart,
                                menuItem,
                                addToCartRequestDto.quantity()
                        )
                );

        return cartMapper.toResponseDto(cartItem);

    }

    private Cart createCart(UUID customerId) {
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        return cartRepository.save(cart);
    }

    private CartItem createCartItem(
            Cart cart,
            MockMenuItem menuItem,
            Integer quantity) {

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .menuItemId(menuItem.menuItemId())
                .cartItemQuantity(quantity)
                .cartItemPrice(menuItem.price())
                .build();
        return cartItemRepository.save(cartItem);
    }

    private void incrementQuantity(
            CartItem cartItem,
            Integer quantityToAdd) {

        Integer newQuantity =
                cartItem.getCartItemQuantity() + quantityToAdd;

        cartItem.setCartItemQuantity(newQuantity);
    }
}
