package com.bonial.challenge.adapter.jdbc;

import com.bonial.challenge.adapter.jdbc.model.RestaurantEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRestaurantRepository extends JpaRepository<RestaurantEntityModel, String> {
}
