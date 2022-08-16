package com.bonial.challenge.core.interfaces.in;

import com.bonial.challenge.domain.Restaurant;

import java.util.Optional;

public interface RestaurantsCommand {
    Optional<Restaurant> get(String id);
    Restaurant save(Restaurant restaurant);

    Restaurant update(Restaurant restaurant, String restaurantId);

    void delete(String id);
}
