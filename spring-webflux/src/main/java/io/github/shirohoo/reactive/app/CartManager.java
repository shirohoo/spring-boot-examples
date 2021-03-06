package io.github.shirohoo.reactive.app;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class CartManager {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public Mono<Rendering> viewCart() {
        return rendering(itemRepository.findAll());
    }

    public Mono<Rendering> viewCart(String itemName, boolean useAnd) {
        return rendering(searchByExample(itemName, useAnd));
    }

    private Mono<Rendering> rendering(Flux<Item> itemId) {
        return Mono.just(Rendering.view("cart")
            .modelAttribute("items", itemId)
            .modelAttribute("cart", findByCartIdDefaultEmpty("MyCart"))
            .build());
    }

    private Mono<Cart> findByCartIdDefaultEmpty(String cartId) {
        return cartRepository.findById(cartId)
            .defaultIfEmpty(Cart.create("MyCart"));
    }

    public Mono<Cart> addItemToCart(String cartId, String itemId) {
        return findByCartIdDefaultEmpty(cartId)
            .flatMap(cart -> getCartItemFrom(cart)
                .filter(eqItemIdInCartItem(itemId))
                .findAny()
                .map(ifAlreadyExistsIncrementBy(cart))
                .orElseGet(ifNotExistsAddTo(itemId, cart)))
            .flatMap(cartRepository::save);
    }

    public Mono<Cart> deleteFromCart(String cartId, String itemId) {
        return findByCartIdDefaultEmpty(cartId)
            .flatMap(cart -> getCartItemFrom(cart)
                .filter(eqItemIdInCartItem(itemId))
                .findAny()
                .map(ifAlreadyExistsDecrementBy(cart))
                .orElse(ifNoQuantityRemove(cart)))
            .flatMap(cartRepository::save);
    }

    private Stream<CartItem> getCartItemFrom(Cart cart) {
        return cart.getCartItems().stream();
    }

    private Predicate<CartItem> eqItemIdInCartItem(String id) {
        return cartItem -> cartItem.getItem().getId().equals(id);
    }

    private Function<CartItem, Mono<Cart>> ifAlreadyExistsIncrementBy(Cart cart) {
        return cartItem -> {
            cartItem.increment();
            return Mono.just(cart);
        };
    }

    private Function<CartItem, Mono<Cart>> ifAlreadyExistsDecrementBy(Cart cart) {
        return cartItem -> {
            cartItem.decrement();
            return Mono.just(cart);
        };
    }

    private Supplier<Mono<Cart>> ifNotExistsAddTo(String itemId, Cart cart) {
        return () -> itemRepository.findById(itemId)
            .map(CartItem::create)
            .map(cartItem -> {
                cart.getCartItems().add(cartItem);
                return cart;
            });
    }

    private Mono<Cart> ifNoQuantityRemove(Cart cart) {
        return Mono.just(cart.removeItems());
    }

    private Flux<Item> searchByExample(String itemName, boolean useAnd) {
        Item item = Item.of(itemName, 0.0);

        ExampleMatcher matcher = useAnd ?
            ExampleMatcher.matchingAll() :
            ExampleMatcher.matchingAny()
                .withStringMatcher(StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);
        return itemRepository.findAll(probe);
    }
}
