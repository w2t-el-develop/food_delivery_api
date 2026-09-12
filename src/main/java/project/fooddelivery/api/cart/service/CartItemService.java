package project.fooddelivery.api.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.cart.dto.response.CartItemResponseDto;
import project.fooddelivery.api.cart.dto.request.RemoveCartItemRequestDto;
import project.fooddelivery.api.cart.dto.request.UpdateQuantityRequestDto;
import project.fooddelivery.api.cart.infrastructure.projection.entity.Cart;
import project.fooddelivery.api.cart.infrastructure.projection.entity.CartItem;
import project.fooddelivery.api.cart.infrastructure.projection.repository.CartItemRepository;
import project.fooddelivery.api.menu.MockMenuItem;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemValidationService cartItemValidationService;
    private final CartValidationService cartValidationService;

    @Transactional
    public CartItem UpdateCartItemQuantity(UUID customerId, UUID cartItemId,
                                                      UpdateQuantityRequestDto updateQuantityRequestDto) {
        cartValidationService.isCartBelongsToCustomer(customerId);
        cartItemValidationService.isCartItemBelongsToCustomer(cartItemId, customerId);
        CartItem cartItem = getCartItem(customerId, cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
        cartItem.setCartItemQuantity(updateQuantityRequestDto.quantity());
        return cartItem;
    }


    @Transactional
    public void removeItem(UUID customerId, UUID cartItemId) {
        cartValidationService.isCartBelongsToCustomer(customerId);
        cartItemValidationService.isCartItemBelongsToCustomer(cartItemId, customerId);
        int deletedRows = cartItemRepository.deleteByCartItemIdAndCustomerId(cartItemId, customerId);
        if (deletedRows == 0) {
            throw new ResourceNotFoundException("Cart item not found");
        }
    }


    @Transactional
    public void batchRemoveItems(UUID customerId, RemoveCartItemRequestDto removeCartItemRequestDto) {
        int deletedRows = cartItemRepository.deleteByCartItemsIdAndCustomerId(customerId, removeCartItemRequestDto.cartItemIds());
        if (deletedRows == 0) {
            throw new ResourceNotFoundException("Cart items not found");
        }
    }


    public CartItem createCartItem(Cart cart, MockMenuItem menuItem, Integer quantity) {
        CartItem cartItem = CartItem.builder().cart(cart).menuItemId(menuItem.menuItemId()).cartItemQuantity(quantity)
                .cartItemPrice(menuItem.price()).build();
        return cartItemRepository.save(cartItem);
    }


    Optional<CartItem> getCartItem(UUID customerId, UUID cartItemId) {
        return cartItemRepository.getCartItemByCartItemIdAndCart_customerId(cartItemId, customerId);
    }

    public List<CartItemResponseDto> getCartItems(UUID CartID) {
        return cartItemRepository.getCartItemsByCart_CartId(CartID);
    }


}
