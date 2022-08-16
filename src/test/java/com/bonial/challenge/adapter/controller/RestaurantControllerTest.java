package com.bonial.challenge.adapter.controller;

import com.bonial.challenge.adapter.controller.exception.NotFoundException;
import com.bonial.challenge.adapter.controller.model.response.RestaurantResponse;
import com.bonial.challenge.adapter.controller.model.response.SearchLocationsResponse;
import com.bonial.challenge.adapter.file.JsonFileAdapter;
import com.bonial.challenge.core.exception.BusinessException;
import com.bonial.challenge.core.interfaces.in.LocationsQuery;
import com.bonial.challenge.core.interfaces.in.RestaurantsCommand;
import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.mocks.MockFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Controller Adapter Test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {
    private final String CHALLENGE_URI = "/api/v1/challenge/locations";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RestaurantsCommand command;
    @MockBean
    private LocationsQuery query;

    @MockBean
    private JsonFileAdapter jsonFileAdapter;

    @Test
    @DisplayName("When user request the nearest restaurants then API responses at least one ")
    void searchLocationsOk() throws Exception {
        SearchLocationsResponse response = MockFactory.getMockSearchLocations();
        when(query.getLocations(any(Coordinates.class))).thenReturn(MockFactory.getMockLocationsDomain());
        this.mockMvc.perform(
                        get(CHALLENGE_URI.concat("/search?x=2&y=2"))

                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("When radius is negative it should raise Internal Error Exception due to body validation")
    void putCreateRestaurantInternalErrorCausedByNegativeRadius() throws Exception {
        this.mockMvc.perform(
                        put(CHALLENGE_URI.concat("/50e1545c-8c95-4d83-82f9-7fcad4a21120"))
                                .content(objectMapper.writeValueAsString(MockFactory.restaurantBadRequestCausedByNegativeRadius()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("It should return restaurant data by id when is found")
    void getRestaurantByIdOk() throws Exception {
        String result = objectMapper.writeValueAsString(RestaurantResponse.restaurantFromDomain(MockFactory.getRestaurantDomain()));
        when(command.get(anyString())).thenReturn(Optional.of(MockFactory.getRestaurantDomain()));
        this.mockMvc.perform(
                        get(CHALLENGE_URI.concat("/50e1545c-8c95-4d83-82f9-7fcad4a21120")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    @DisplayName("It should throw exception because restaurant does not exist")
    void getRestaurantNotFound() throws Exception {
        when(query.getLocations(any(Coordinates.class))).thenThrow(NotFoundException.class);
        this.mockMvc.perform(
                        get(CHALLENGE_URI.concat("/50e1545c-8c95-4d83-82f9-7fcad4a21120")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}