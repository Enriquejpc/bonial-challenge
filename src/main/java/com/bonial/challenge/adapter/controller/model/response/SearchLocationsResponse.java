package com.bonial.challenge.adapter.controller.model.response;

import com.bonial.challenge.domain.Locations;
import com.bonial.challenge.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchLocationsResponse {
    String userLocations;
    List<RestaurantResponse> locations;

    public static SearchLocationsResponse fromDomain(Locations locations) {
        return SearchLocationsResponse.builder()
                .userLocations(locations.getUserCoordinates().toString())
                .locations(locations.getRestaurants()
                        .stream().map(RestaurantResponse::locationsFromDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}
