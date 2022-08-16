package com.bonial.challenge.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Optional;
import java.util.UUID;

@Value
@Builder
public class Restaurant {
    String id;
    String name;
    String type;
    String openingHours;
    Coordinates coordinates;
    Double radius;
    String image;
    @With
    Double distance;
}
