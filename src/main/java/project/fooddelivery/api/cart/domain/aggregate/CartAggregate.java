package project.fooddelivery.api.cart.domain.aggregate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import project.fooddelivery.api.cart.domain.event.*;
import project.fooddelivery.api.cart.application.model.StoredCartEvent;
import project.fooddelivery.api.cart.domain.exception.CartAlreadyCreatedException;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;

import java.util.*;

@Getter
@Setter
public class CartAggregate {
    private UUID cartId;
    private UUID customerId;
    private boolean created;
    private Long version;

    private final Map<UUID, CartLine> items = new HashMap<>();
    private final List<CartEvent> uncommittedEvents = new ArrayList<>();


    public static CartAggregate rehydrate(List<StoredCartEvent> history) {
        CartAggregate cartAggregate = new CartAggregate();
        history.stream()
                .sorted(Comparator.comparingLong(
                        StoredCartEvent::aggregateVersion
                ))
                .forEach(storedEvent -> {
                    cartAggregate.apply(storedEvent.payload());
                    cartAggregate.version = storedEvent.aggregateVersion();
                });
        return cartAggregate;
    }


    public void create(@NotNull(message = "Cart ID is required")
                       UUID cartId,
                       @NotNull(message = "Customer ID is required")
                       UUID customerId) {
        if(created){
            throw new CartAlreadyCreatedException("Cart already created");
        }
        this.cartId = cartId;
        this.customerId = customerId;
        this.created = true;
        raise(CartCreated.buildCartCreatedEvent(cartId, customerId));
    }


    private UUID addItemToCart(@NotNull(message = "Menu item ID is required")
                               UUID menuItemId,
                               @NotNull(message = "Menu item Name is required")
                               String menuItemName,
                               @NotNull(message = "Menu item price is required")
                               @Positive(message = "Menu item price must be greater than zero")
                               Double menuItemPrice,
                               @NotNull(message = "quantity is required")
                               @Positive(message = "quantity must be greater than zero")
                               Integer quantity){
        ensureCartExists();
        UUID cartItemId = UUID.randomUUID();
        raise(CartItemAdded.buildCartItemAddedEvent(cartItemId, menuItemId, menuItemName, menuItemPrice, quantity));
        return cartItemId;
    }


    public void updateQuantity(@NotNull(message = "Cart item ID is required")
                               UUID cartItemId,
                               @NotNull(message = "quantity is required")
                               @Positive(message = "quantity must be greater than zero")
                               Integer newQuantity){
        ensureCartExists();
        ensureCartItemExists(cartItemId);
        raise(CartItemQuantityUpdated.buildCartItemQuantityChangedEvent(cartItemId, newQuantity));
    }

    public void removeItem(@NotNull(message = "Cart item ID is required") UUID cartItemId){
        ensureCartExists();
        ensureCartItemExists(cartItemId);
        raise(CartItemRemoved.buildCarItemRemovedEvent(cartId, cartItemId));
    }

    public void removeBatchItems(@NotNull(message = "Cart item Ids collection must not be null")
                                 @NotEmpty(message = "Cart item Ids collection must not be empty")
                                 Set<UUID> cartItemIds){
        ensureCartExists();
        Set<UUID> cartItemIdsToRemove = new HashSet<>(cartItemIds);
        cartItemIdsToRemove.removeAll(items.keySet());
        if(!cartItemIdsToRemove.isEmpty()){
            throw new ResourceNotFoundException("cart item not found");
        }
        cartItemIds.forEach(this::removeItem);
    }

    public void clearCart(){
        ensureCartExists();
        if(items.isEmpty()){
            return;
        }
        raise(CartCleared.buildCartClearedEvent(cartId));
    }

    private void ensureCartExists() {
        if (!created) {
            throw new ResourceNotFoundException("Cart not found");
        }
    }

    private void ensureCartItemExists(UUID cartItemId) {
        if(!items.containsKey(cartItemId)){
            throw new ResourceNotFoundException("Cart item not found");
        }
    }

    private Optional<CartLine> findByCartItemId(UUID cartItemId) {
        return items.values()
                .stream()
                .filter(line -> line.cartItemId().equals(cartItemId))
                .findFirst();
    }


    private void apply(CartEvent event) {
        switch (event) {
            case CartCreated cartCreated -> apply(cartCreated);

            case CartItemAdded cartItemAdded -> apply(cartItemAdded);

            case CartItemQuantityUpdated quantityChanged -> apply(quantityChanged);

            case CartItemRemoved cartItemRemoved -> apply(cartItemRemoved);

            case CartCleared cartCleared -> apply(cartCleared);
        }
    }

    private void apply(CartCreated event) {
        this.cartId = event.cartId();
        this.customerId = event.customerId();
        this.created = true;
    }

    private void apply(CartItemAdded cartItemAdded) {
        CartLine cartLine = CartLine.buildCartLine(
                cartItemAdded.cartItemId(),
                cartItemAdded.menuItemId(),
                cartItemAdded.menuItemName(),
                cartItemAdded.menuItemPrice(),
                cartItemAdded.quantity()
        );
        items.put(cartItemAdded.cartItemId(), cartLine);
    }

    private void apply(CartItemQuantityUpdated cartItemQuantityUpdated) {
        CartLine existingLine = findByCartItemId(cartItemQuantityUpdated
                .cartItemId()).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        items.put(cartItemQuantityUpdated.cartItemId(),
                existingLine.updateQuantity(cartItemQuantityUpdated.newQuantity()));
    }

    private void apply(CartItemRemoved cartItemRemoved) {
        items.remove(cartItemRemoved.cartItemId());
    }

    private void apply(CartCleared cartCleared) {
        items.clear();
    }

    private void raise(CartEvent cartEvent) {
        apply(cartEvent);
        uncommittedEvents.add(cartEvent);
    }

    public void markEventsAsCommitted(long newVersion) {
        version = newVersion;
        uncommittedEvents.clear();
    }

    public List<CartEvent> getUncommittedEvents() {
        return List.copyOf(uncommittedEvents);
    }

    public Map<UUID, CartLine> getItems() {
        return Map.copyOf(items);
    }


}
