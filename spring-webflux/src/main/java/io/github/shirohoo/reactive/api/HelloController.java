package io.github.shirohoo.reactive.api;

import io.github.shirohoo.reactive.domain.Dish;
import io.github.shirohoo.reactive.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final Restaurant restaurant;

    @GetMapping(path = "/foods", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Dish> serveDishes() {
        return restaurant.getDishes()
            .map(Dish::deliver);
    }
}
