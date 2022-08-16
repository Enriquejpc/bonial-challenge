package com.bonial.challenge.core.interfaces.out;

import com.bonial.challenge.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantsRepository {

    Restaurant saveOrUpdate(Restaurant restaurant);

    List<Restaurant> getAll();

    Optional<Restaurant> getByRestaurantId(String restaurantId);

    void delete(String restaurantId);
}
