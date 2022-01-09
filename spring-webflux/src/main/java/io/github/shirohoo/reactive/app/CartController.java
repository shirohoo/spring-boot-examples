package io.github.shirohoo.reactive.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
class CartController {
    private final CartManager cartManager;

    @GetMapping
    Mono<Rendering> viewCart() {
        return cartManager.viewCart();
    }

    @GetMapping("/items")
    Mono<Rendering> search(
        @RequestParam(required = false) String itemName,
        @RequestParam boolean useAnd
    ) {
        return cartManager.viewCart(itemName, useAnd);
    }

    @PostMapping("/items/{itemId}")
    Mono<String> addToCart(@PathVariable String itemId) {
        return cartManager.addItemToCart("MyCart", itemId)
            .thenReturn("redirect:/carts");
    }
}