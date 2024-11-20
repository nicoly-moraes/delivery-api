package com.delivery.api.dtos;

import com.delivery.api.entities.UserType;

public record LoginResponseDto(
    Integer id,
    String email,
    UserType type,
    String token
) {
}
