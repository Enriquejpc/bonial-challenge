package com.bonial.challenge.adapter.controller.model.request;

import com.bonial.challenge.adapter.controller.exception.ParametersException;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Builder
@Data
@JsonIgnoreProperties()
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RestaurantRequestModel {
    @NotEmpty(message = "Id is mandatory")
    @NotNull(message = "Id is mandatory")
    String id;

    @NotEmpty(message = "Restaurant name is mandatory")
    @NotNull(message = "Restaurant name is mandatory")
    String name;

    @NotEmpty(message = "Restaurant type is mandatory")
    @NotNull(message = "Restaurant type is mandatory")
    String type;

    @NotEmpty(message = "Hours is mandatory")
    @NotNull(message = "Hours is mandatory")
    String openingHours;

    @NotEmpty(message = "Coordinates is mandatory")
    @NotNull(message = "Coordinates is mandatory")
    String coordinates;

    @Positive(message = "Must be positive")
    @NotNull(message = "Radius is mandatory")
    Double radius;

    @NotEmpty(message = "Image is mandatory")
    @NotNull(message = "Image is mandatory")
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
                .coordinates(CoordinateRequestModel.toDomain(xValue, yValue))
                .build();
    }

}
