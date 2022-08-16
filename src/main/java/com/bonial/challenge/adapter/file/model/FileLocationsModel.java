package com.bonial.challenge.adapter.file.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FileLocationsModel {
    @JsonProperty("locations")
    public List<RestaurantJsonModel> locations;
}
