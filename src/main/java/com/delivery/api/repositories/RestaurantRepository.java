package com.delivery.api.repositories;

import com.delivery.api.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}
