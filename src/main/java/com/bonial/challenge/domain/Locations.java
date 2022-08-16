package com.bonial.challenge.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Locations {
    Coordinates userCoordinates;
    List<Restaurant> restaurants;
}
