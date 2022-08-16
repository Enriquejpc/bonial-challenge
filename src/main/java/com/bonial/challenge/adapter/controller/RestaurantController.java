package com.bonial.challenge.adapter.controller;

import com.bonial.challenge.adapter.controller.exception.NotFoundException;
import com.bonial.challenge.adapter.controller.model.request.RestaurantRequestModel;
import com.bonial.challenge.adapter.controller.model.response.RestaurantResponse;
import com.bonial.challenge.adapter.controller.model.response.SearchLocationsResponse;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.interfaces.in.LocationsQuery;
import com.bonial.challenge.core.interfaces.in.RestaurantsCommand;
import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.domain.Locations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/challenge/locations")
public class RestaurantController {
    private final LocationsQuery locationsQuery;
    private final RestaurantsCommand restaurantsCommand;

    public RestaurantController(LocationsQuery locationsQuery, RestaurantsCommand restaurantsCommand) {
        this.locationsQuery = locationsQuery;
        this.restaurantsCommand = restaurantsCommand;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchLocationsResponse> getLocations(@RequestParam(name = "x") String xAxis, @RequestParam(name = "y") String yAxis) {
        log.info("Retrieving required information for x-axis:: {} and y-axis:: {}", xAxis, yAxis);

        Locations locations = locationsQuery.getLocations(
                Coordinates.builder()
                        .xAxis(Double.valueOf(xAxis))
                        .yAxis(Double.valueOf(yAxis))
                        .build());

        return ResponseEntity.ok().body(SearchLocationsResponse.fromDomain(locations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantDetails(@PathVariable("id") String restaurantId) {
        log.info("Retrieving required information for restaurant:: {}", restaurantId);
        var restaurant =  restaurantsCommand.get(restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND_ERROR));
        var response = RestaurantResponse.restaurantFromDomain(restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> save(@PathVariable("id") String restaurantId, @RequestBody @Validated RestaurantRequestModel body) {
        log.info("Restaurant:: body received {}", body);
        var restaurant =  restaurantsCommand.update(body.toDomain(), restaurantId);

        var response = RestaurantResponse.restaurantFromDomain(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

