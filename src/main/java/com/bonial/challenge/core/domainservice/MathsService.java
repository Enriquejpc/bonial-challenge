package com.bonial.challenge.core.domainservice;

import com.bonial.challenge.domain.Coordinates;
import org.springframework.stereotype.Component;

@Component
public class MathsService {

    public Double distanceBetweenTwoPoints(Coordinates restaurantCoordinates, Coordinates userCoordinates) {
        Double yAxis = coordinateSubtract(restaurantCoordinates.getYAxis(), userCoordinates.getYAxis());
        Double xAxis = coordinateSubtract(restaurantCoordinates.getXAxis() , userCoordinates.getXAxis());
        return Math.hypot(yAxis, xAxis);
    }

    private Double coordinateSubtract(Double endCoordinate, Double startCoordinate) {
        return Math.abs(endCoordinate - startCoordinate);
    }
}
