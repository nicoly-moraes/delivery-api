package com.delivery.api.utils;

import com.delivery.api.entities.UserType;
import com.delivery.api.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepository userRepository;

    private UserType type;

    private long ignoreId;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.ignoreId = constraintAnnotation.ignoreId();
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }

        if (ignoreId != -1) {
            return !userRepository.existsByEmailAndTypeAndIdNot(email, type, ignoreId);
        }

        return !userRepository.existsByEmailAndType(email, type);
    }
}
