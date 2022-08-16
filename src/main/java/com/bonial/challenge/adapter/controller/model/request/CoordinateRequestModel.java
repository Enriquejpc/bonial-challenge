package com.bonial.challenge.adapter.controller.model.request;

import com.bonial.challenge.adapter.controller.exception.ParametersException;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.domain.Coordinates;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class CoordinateRequestModel {

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
