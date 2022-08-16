package com.bonial.challenge.core.businesscase;

import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.exception.BusinessException;
import com.bonial.challenge.core.interfaces.in.RestaurantsCommand;
import com.bonial.challenge.core.interfaces.out.RestaurantsRepository;
import com.bonial.challenge.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RestaurantsCommandUseCase implements RestaurantsCommand {
    private final RestaurantsRepository restaurantsRepository;

    public RestaurantsCommandUseCase(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    @Override
    public Optional<Restaurant> get(String id) {
        return restaurantsRepository.getByRestaurantId(id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        log.info("Checking if exists restaurant:: {}", restaurant);
        Optional<Restaurant> resto = restaurantsRepository.getByRestaurantId(restaurant.getId());
        if (resto.isPresent()) {
            throw new BusinessException(ErrorCode.RESOURCE_DUPLICATE_ERROR);
        }
        log.info("Saving restaurant:: {}", restaurant);
        return restaurantsRepository.saveOrUpdate(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant, String restaurantId) {
        var resto = (restaurantsRepository.getByRestaurantId(restaurantId))
                .orElse(restaurant);
        var updatedRestaurant = Restaurant.builder()
                .id(resto.getId())
                .name(restaurant.getName())
                .type(restaurant.getType())
                .image(restaurant.getImage())
                .openingHours(restaurant.getOpeningHours())
                .coordinates(restaurant.getCoordinates())
                .radius(restaurant.getRadius())
                .build();
        log.info("Updating restaurant:: {}", restaurant);
        return restaurantsRepository.saveOrUpdate(updatedRestaurant);
    }

    @Override
    public void delete(String id) {
        var resto = (restaurantsRepository.getByRestaurantId(id))
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND_ERROR));
        restaurantsRepository.delete(resto.getId());
    }
}
