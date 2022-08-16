package com.bonial.challenge.core.businesscase;

import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.domainservice.MathsService;
import com.bonial.challenge.core.exception.BusinessException;
import com.bonial.challenge.core.interfaces.in.LocationsQuery;
import com.bonial.challenge.core.interfaces.out.RestaurantsRepository;
import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.domain.Locations;
import com.bonial.challenge.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GetLocationsUseCase implements LocationsQuery {

    private final RestaurantsRepository restaurantsRepository;
    private final MathsService mathsService;

    public GetLocationsUseCase(RestaurantsRepository restaurantsRepository, MathsService mathsService) {
        this.restaurantsRepository = restaurantsRepository;
        this.mathsService = mathsService;
    }

    @Override
    public Locations getLocations(Coordinates userCoordinates) {
        List<Restaurant> restaurantList =  restaurantsRepository.getAll();

        List<Restaurant> restaurantsWithDistance = Optional.of(restaurantList.stream().parallel()
                .map(it -> calculateDistance(it, userCoordinates))
                .filter(it -> it.getDistance() > 0)
                .filter(it -> it.getDistance()<= it.getRadius())
                .sorted(Comparator.comparingDouble(Restaurant::getDistance))
                .collect(Collectors.toList())).filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BusinessException(ErrorCode.NO_LOCATIONS));

        log.info("Sorted list:: {}", restaurantsWithDistance);

        return Locations.builder()
                .userCoordinates(userCoordinates)
                .restaurants(restaurantsWithDistance)
                .build();
    }

    private Restaurant calculateDistance(Restaurant restaurant, Coordinates userCoordinates) {
        Double distance = Optional.ofNullable(mathsService.distanceBetweenTwoPoints(restaurant.getCoordinates(), userCoordinates)).orElse(Double.NaN);
        log.info("Distance: {} for the restaurant: {}", distance, restaurant.getName());
        return restaurant.withDistance(distance);
    }
}
