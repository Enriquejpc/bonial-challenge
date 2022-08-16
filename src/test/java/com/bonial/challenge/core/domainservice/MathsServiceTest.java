package com.bonial.challenge.core.domainservice;

import com.bonial.challenge.mocks.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Calculate Distance between two points Use Case Test")
@ExtendWith(MockitoExtension.class)
class MathsServiceTest {

    @Test
    @DisplayName("It should return result value of distance between user and restaurants")
    void distanceBetweenTwoPoints_Ok() {
        MathsService mathsService = new MathsService();

        Double distance = mathsService.distanceBetweenTwoPoints(MockFactory.getRestaurantCoordinates(), MockFactory.getUserCoordinates());

        assertThat(distance).isNotNull();
        assertThat(distance).isEqualTo(1.0);

    }

}