package com.bonial.challenge.adapter.file.model;

import com.bonial.challenge.adapter.controller.exception.ParametersException;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.domain.Coordinates;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoordinateJsonModel {

    private Double xAxis;
    private Double yAxis;

    public static Coordinates toDomain(Double x, Double y) {
        if (x < 0 || y < 0)
            throw new ParametersException(ErrorCode.BAD_REQUEST);
        return Coordinates.builder()
                .xAxis(x)
                .yAxis(y)
                .build();
    }

    @Override
    public String toString() {
        return "x=" + xAxis + ", y=" + yAxis;
    }
}
