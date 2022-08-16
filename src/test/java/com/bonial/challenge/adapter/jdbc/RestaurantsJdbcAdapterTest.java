package com.bonial.challenge.adapter.jdbc;

import com.bonial.challenge.adapter.file.JsonFileAdapter;
import com.bonial.challenge.adapter.jdbc.model.RestaurantEntityModel;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.exception.BusinessException;
import com.bonial.challenge.mocks.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Component
@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantsJdbcAdapter Repository test")
class RestaurantsJdbcAdapterTest {

    private final JpaRestaurantRepository jpaRepository = Mockito.mock(JpaRestaurantRepository.class);

    private final RestaurantsJdbcAdapter adapter = new RestaurantsJdbcAdapter(jpaRepository);

    @MockBean
    private JsonFileAdapter jsonFileAdapter;

    @Test
    @DisplayName("When save is called and is executed. It does not raise exception")
    void saveRestaurantIsOk() {
        when(jpaRepository.save(any(RestaurantEntityModel.class))).thenReturn(RestaurantEntityModel.fromDomain(MockFactory.getRestaurantDomain()));
        Throwable throwable = catchThrowable(() -> adapter.saveOrUpdate(MockFactory.getRestaurantDomain()));
        assertThat(throwable).doesNotThrowAnyException();
        verify(jpaRepository, times(1)).save(any(RestaurantEntityModel.class));
    }

    @Test
    @DisplayName("When save is called but an exception occurs")
    void saveRestaurantThrowsException() {
        doThrow(new RuntimeException()).when(jpaRepository).save(any(RestaurantEntityModel.class));
        Throwable throwable = catchThrowable(() -> adapter.saveOrUpdate(MockFactory.getRestaurantDomain()));
        assertThat(throwable)
                .isInstanceOf(BusinessException.class).hasMessage(ErrorCode.DATABASE_CONNECTION_ERROR.getReasonPhrase());
    }
}