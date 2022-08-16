package com.bonial.challenge.adapter.controller.model.response;

import com.bonial.challenge.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponse {

    String id;
    String name;
    String type;
    String coordinates;
    Double distance;
    String openingHours;
    String image;

    public static RestaurantResponse locationsFromDomain(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .coordinates(restaurant.getCoordinates().toString())
                .distance(restaurant.getDistance())
                .build();
    }

    public static RestaurantResponse restaurantFromDomain(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .name(restaurant.getName())
                .type(restaurant.getType())
                .id(restaurant.getId())
                .openingHours(restaurant.getOpeningHours().toUpperCase())
                .image(restaurant.getImage())
                .coordinates(restaurant.getCoordinates().toString())
                .build();
    }
}
