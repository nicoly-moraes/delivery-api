package com.delivery.api.utils;

import com.delivery.api.repositories.RestaurantRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    @Autowired
    private RestaurantRepository restaurantRepository;

    private long ignoreId;

    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
        this.ignoreId = constraintAnnotation.ignoreId();
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true;
        }

        if (ignoreId != -1) {
            return !restaurantRepository.existsByPhoneNumberAndIdNot(phoneNumber, ignoreId);
        }

        return !restaurantRepository.existsByPhoneNumber(phoneNumber);
    }
}
