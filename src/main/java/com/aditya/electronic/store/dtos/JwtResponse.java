package com.aditya.electronic.store.dtos;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    UserDto user;
    private RefreshTokenDto refreshToken;
}
