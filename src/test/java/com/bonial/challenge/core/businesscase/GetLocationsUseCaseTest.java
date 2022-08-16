package com.bonial.challenge.core.businesscase;

import com.bonial.challenge.adapter.file.JsonFileAdapter;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.domainservice.MathsService;
import com.bonial.challenge.core.exception.BusinessException;
import com.bonial.challenge.core.interfaces.out.RestaurantsRepository;
import com.bonial.challenge.domain.Locations;
import com.bonial.challenge.mocks.MockFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Execute differents methods in accordance with user request")
@ExtendWith(MockitoExtension.class)
class GetLocationsUseCaseTest {

    @Mock
    RestaurantsRepository restaurantsRepository;

    @Mock
    MathsService mathsService;

    @MockBean
    private JsonFileAdapter jsonFileAdapter;

    GetLocationsUseCase getLocationsUseCase = null;


    @Test
    @DisplayName("It should retrieve at least one ocurrence in accordance user coordinates")
    void retrieveAtLeastOneOccurrence_Ok() {
        getLocationsUseCase = new GetLocationsUseCase(restaurantsRepository, mathsService);
        when(restaurantsRepository.getAll()).thenReturn(List.of(MockFactory.getRestaurantDomain()));
        when(mathsService.distanceBetweenTwoPoints(MockFactory.getRestaurantCoordinates(), MockFactory.getUserCoordinates())).thenReturn(2.0);
        Locations locations = getLocationsUseCase.getLocations(MockFactory.getUserCoordinates());

        assertThat(locations).isNotNull();
        assertThat(locations.getRestaurants().size()).isEqualTo(1);
        verify(restaurantsRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("When search locations does not have occurrences should raise an exception")
    void noLocationsFoundException() {
        getLocationsUseCase = new GetLocationsUseCase(restaurantsRepository, mathsService);
        when(restaurantsRepository.getAll()).thenReturn(List.of(MockFactory.getRestaurantDomain()));
        Throwable thrown = catchThrowable(() -> getLocationsUseCase.getLocations(MockFactory.getUserCoordinates()));
        assertThat(thrown).isInstanceOf(BusinessException.class).hasMessage(ErrorCode.NO_LOCATIONS.getReasonPhrase());
    }
}