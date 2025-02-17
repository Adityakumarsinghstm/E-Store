package com.aditya.electronic.store.services;

import com.aditya.electronic.store.dtos.RefreshTokenDto;
import com.aditya.electronic.store.dtos.UserDto;

public interface RefreshTokenService {

        //create
        RefreshTokenDto createRefreshToken(String username);

        // find by token
        RefreshTokenDto findByToken(String token);
//verify

        RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenDto);

        UserDto getUser(RefreshTokenDto dto);


}
