package com.bonial.challenge.adapter.jdbc;

import com.bonial.challenge.adapter.jdbc.model.RestaurantEntityModel;
import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.exception.BusinessException;
import com.bonial.challenge.core.interfaces.out.RestaurantsRepository;
import com.bonial.challenge.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RestaurantsJdbcAdapter implements RestaurantsRepository {

    private final JpaRestaurantRepository jpaRestaurantRepository;

    public RestaurantsJdbcAdapter(JpaRestaurantRepository jpaRestaurantRepository) {
        this.jpaRestaurantRepository = jpaRestaurantRepository;
    }

    @Override
    public Restaurant saveOrUpdate(Restaurant restaurant) {
        log.info("Saving restaurant :: {}", restaurant);
        try {
            jpaRestaurantRepository.save(RestaurantEntityModel.fromDomain(restaurant));
            return restaurant;
        } catch (Exception exception) {
            log.error("Exception risen::", exception);
            throw new BusinessException(ErrorCode.DATABASE_CONNECTION_ERROR);
        }
    }

    @Override
    public List<Restaurant> getAll() {
        List<RestaurantEntityModel> restaurantEntityModels = jpaRestaurantRepository.findAll();
        return restaurantEntityModels.stream()
                .map(RestaurantEntityModel::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Restaurant> getByRestaurantId(String restaurantId) {
        log.info("Retrieving restaurant by id:: {}", restaurantId);
        Optional<RestaurantEntityModel> restaurant = jpaRestaurantRepository.findById(restaurantId);
        return restaurant.map(RestaurantEntityModel::toDomain);
    }

    @Override
    public void delete(String restaurantId) {
        log.info("Deleting restaurant with id :: {}", restaurantId);
        jpaRestaurantRepository.deleteById(restaurantId);
    }
}
