package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.repositories.UserRepository;
import com.aditya.electronic.store.services.UserService;
import jakarta.transaction.UserTransaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
   private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
       User user = dtoToEnitity(userDto);
       User savedUser = userRepository.save(user);
       UserDto userDto1 = entityToDto(savedUser);
        return userDto1;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found With This userId !!!"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());

        User updatedUser =  userRepository.save(user);

        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found With This userId !!!"));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<User> pages = userRepository.findAll(pageable);
        List<User> users = pages.getContent();
        List<UserDto> userDto = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDto;
    }

    @Override
    public UserDto getUserById(String userId) {
       User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with this id !!!!"));
        return entityToDto(user );
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User Not found with this email !!!"));
        UserDto userDto =  entityToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users =  userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
        UserDto userDto = mapper.map(savedUser,UserDto.class);
        return userDto;
    }

    private User dtoToEnitity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .gender(userDto.getGender())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName())
//                .build();
        User user = mapper.map(userDto,User.class);
        return user;
    }
}
