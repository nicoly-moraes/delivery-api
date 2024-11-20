package com.delivery.api.dtos;

import com.delivery.api.entities.UserType;
import com.delivery.api.utils.UniqueEmail;
import com.delivery.api.utils.UniquePhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRestaurantDto(
    @NotBlank
    String name,

    String image,

    @NotBlank
    String postalCode,

    @NotBlank
    String addressLine1,

    String addressLine2,

    @NotBlank
    String city,

    @NotBlank
    String state,

    @NotBlank
    String country,

    @NotNull
    Double deliveryPrice,

    @NotNull
    Double deliveryRadius,

    @NotNull
    Double latitude,

    @NotNull
    Double longitude,

    @NotBlank
    @UniquePhoneNumber()
    String phoneNumber,

    @Email
    @UniqueEmail(type = UserType.RESTAURANT)
    @NotBlank
    String email,

    @NotBlank
    String password
) {
}
