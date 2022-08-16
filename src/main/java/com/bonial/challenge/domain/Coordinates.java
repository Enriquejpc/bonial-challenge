package com.bonial.challenge.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Coordinates {
    Double xAxis;
    Double yAxis;

    @Override
    public String toString() {
        return "x=" + xAxis + ", y=" + yAxis;
    }
}
