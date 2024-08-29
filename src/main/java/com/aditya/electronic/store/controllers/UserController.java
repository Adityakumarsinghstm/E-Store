package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.ApiResponseMessage;
import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody UserDto userDto){
        UserDto updatedUserDto = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
    {
       userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User Deleted Successfully !!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
       return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId)
    {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords)
    {
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }
}
