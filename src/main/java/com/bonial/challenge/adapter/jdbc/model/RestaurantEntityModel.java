package com.bonial.challenge.adapter.jdbc.model;

import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "RESTAURANT")
public class RestaurantEntityModel {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "radius", nullable = false)
    private BigDecimal radius;

    @Column(name = "x_axis", nullable = false)
    private Double xAxis;

    @Column(name = "y_axis", nullable = false)
    private Double yAxis;

    @Column(name = "opening_hours", nullable = false)
    private String openingHours;


    public static RestaurantEntityModel fromDomain(Restaurant restaurant) {

        return RestaurantEntityModel.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .type(restaurant.getType())
                .xAxis(restaurant.getCoordinates().getXAxis())
                .yAxis(restaurant.getCoordinates().getYAxis())
                .radius(BigDecimal.valueOf(restaurant.getRadius()))
                .openingHours(restaurant.getOpeningHours())
                .image(restaurant.getImage())
                .build();
    }

    public static Restaurant toDomain(RestaurantEntityModel entityModel) {
        Coordinates coordinates = Coordinates.builder()
                .xAxis(entityModel.getXAxis())
                .yAxis(entityModel.getYAxis())
                .build();
        return Restaurant.builder()
                .id(entityModel.getId())
                .name(entityModel.getName())
                .type(entityModel.getType())
                .coordinates(coordinates)
                .radius(entityModel.getRadius().doubleValue())
                .image(entityModel.getImage())
                .openingHours(entityModel.getOpeningHours())
                .build();
    }

}
