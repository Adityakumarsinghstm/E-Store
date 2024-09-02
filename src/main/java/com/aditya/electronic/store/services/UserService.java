package com.aditya.electronic.store.services;

import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.entities.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto> getAllUser(int pageNumber,int pageSize);

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);

}
