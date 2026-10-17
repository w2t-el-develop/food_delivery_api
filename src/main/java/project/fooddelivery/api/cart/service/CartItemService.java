package project.fooddelivery.api.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.cart.dto.RemoveCartItemRequestDto;
import project.fooddelivery.api.cart.dto.UpdateQuantityRequestDto;
import project.fooddelivery.api.cart.entity.Cart;
import project.fooddelivery.api.cart.entity.CartItem;
import project.fooddelivery.api.cart.repository.CartItemRepository;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.menu.entity.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemHelperService cartItemHelperService;
    private final CartHelperService cartHelperService;

    @Transactional
    public CartItem UpdateCartItemQuantity(UUID customerId, UUID cartItemId,
                                           UpdateQuantityRequestDto updateQuantityRequestDto) {
        cartHelperService.isCartBelongsToCustomer(customerId);
        cartItemHelperService.isCartItemBelongsToCustomer(cartItemId, customerId);
        CartItem cartItem = getCartItem(customerId, cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
        cartItem.setCartItemQuantity(updateQuantityRequestDto.quantity());
        return cartItem;
    }


    @Transactional
    public void removeItem(UUID customerId, UUID cartItemId) {
        cartHelperService.isCartBelongsToCustomer(customerId);
        cartItemHelperService.isCartItemBelongsToCustomer(cartItemId, customerId);
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

    public CartItem createCartItem(Cart cart, MenuItem menuItem, Integer quantity) {
        CartItem cartItem = buildCartItem(cart, menuItem, quantity);
        return cartItemRepository.save(cartItem);
    }

    CartItem buildCartItem(Cart cart, MenuItem menuItem, Integer quantity) {
        return CartItem.builder()
                .cart(cart)
                .menuItemId(menuItem.getMenuItemId()).cartItemQuantity(quantity)
                .cartItemPrice(menuItem.getMenuItemPrice()).build();
    }

    Optional<CartItem> getCartItem(UUID customerId, UUID cartItemId) {
        return cartItemRepository.getCartItemByCartItemIdAndCart_customerId(cartItemId, customerId);
    }

    public List<CartItemResponseDto> getCartItems(UUID CartID) {
        return cartItemRepository.getCartItemsByCart_CartId(CartID);
    }


}
