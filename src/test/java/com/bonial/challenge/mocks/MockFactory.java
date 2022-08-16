package com.bonial.challenge.mocks;

import com.bonial.challenge.adapter.controller.model.request.RestaurantRequestModel;
import com.bonial.challenge.adapter.controller.model.response.RestaurantResponse;
import com.bonial.challenge.adapter.controller.model.response.SearchLocationsResponse;
import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.domain.Locations;
import com.bonial.challenge.domain.Restaurant;

import java.util.List;


public class MockFactory {

    public static Coordinates getRestaurantCoordinates() {
        return Coordinates.builder()
                .xAxis(2.0)
                .yAxis(3.0)
                .build();
    }

    public static Coordinates getUserCoordinates() {
        return Coordinates.builder()
                .xAxis(2.0)
                .yAxis(2.0)
                .build();
    }

    public static RestaurantRequestModel restaurantRequest() {
        return RestaurantRequestModel.builder()
                .coordinates("x=2.0, y=3.0")
                .id("50e1545c-8c95-4d83-82f9-7fcad4a21120")
                .image("https://tinyurl.com")
                .name("Restaurant 01")
                .radius(5.0)
                .type("Restaurant")
                .openingHours("09:00AM-09:00PM")
                .build();
    }

    public static RestaurantRequestModel restaurantBadRequestCausedByNegativeRadius() {
        return RestaurantRequestModel.builder()
                .coordinates("x=2.0, y=3.0")
                .id("50e1545c-8c95-4d83-82f9-7fcad4a21120")
                .image("https://tinyurl.com")
                .name("Restaurant 01")
                .radius(-5.0)
                .type("Restaurant")
                .openingHours("09:00AM-09:00PM")
                .build();
    }

    public static Restaurant getRestaurantDomain() {
        return restaurantRequest().toDomain().withDistance(3.0);
    }

    public static SearchLocationsResponse getMockSearchLocations() {
        return SearchLocationsResponse.builder()
                .userLocations("x=2.0, y=2.0")
                .locations(List.of(RestaurantResponse.locationsFromDomain(getRestaurantDomain())))
                .build();
    }

    public static Locations getMockLocationsDomain() {
        return Locations.builder()
                .userCoordinates(getUserCoordinates())
                .restaurants(List.of(getRestaurantDomain()))
                .build();
    }
}
