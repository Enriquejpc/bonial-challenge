package com.bonial.challenge.adapter.file.model;

import com.bonial.challenge.adapter.controller.model.request.CoordinateRequestModel;
import com.bonial.challenge.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;



public class RestaurantJsonModel {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    String type;

    @JsonProperty("opening-hours")
    String openingHours;

    @JsonProperty("coordinates")
    String coordinates;

    @JsonProperty("radius")
    Double radius;

    @JsonProperty("image")
    String image;

    public Restaurant toDomain() {
        Double xValue = Double.valueOf(coordinates.split(",")[0].split("=")[1]);
        Double yValue = Double.valueOf(coordinates.split(",")[1].split("=")[1]);

        return Restaurant.builder()
                .id(id)
                .name(name)
                .type(type)
                .openingHours(openingHours)
                .radius(radius)
                .image(image)
                .coordinates(CoordinateJsonModel.toDomain(xValue, yValue))
                .build();
    }
}
