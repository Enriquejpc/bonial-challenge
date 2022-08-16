package com.bonial.challenge.adapter.file;

import com.bonial.challenge.adapter.file.model.FileLocationsModel;
import com.bonial.challenge.adapter.file.model.RestaurantJsonModel;
import com.bonial.challenge.core.interfaces.in.RestaurantsCommand;
import com.bonial.challenge.domain.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JsonFileAdapter {

    @Autowired
    ObjectMapper mapper;

    private final RestaurantsCommand restaurantsCommand;

    public JsonFileAdapter(RestaurantsCommand restaurantsCommand) {
        this.restaurantsCommand = restaurantsCommand;
    }

    public void bulkData(String path) throws JsonProcessingException {
        String data = FileReader.read(path);
        FileLocationsModel restaurants = mapper.readValue(data, FileLocationsModel.class);
        CompletableFuture.allOf(restaurants.locations.parallelStream()
                .map(RestaurantJsonModel::toDomain)
                .collect(Collectors.toList()).parallelStream()
                .map(it -> CompletableFuture.supplyAsync(() -> restaurantsCommand.save(it))).toArray(CompletableFuture[]::new)).join();
        log.info("Data loaded");
    }
}
