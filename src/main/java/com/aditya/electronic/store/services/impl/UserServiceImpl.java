package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.config.AppConstants;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.entities.Role;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.helper.Helper;
import com.aditya.electronic.store.repositories.RoleRepository;
import com.aditya.electronic.store.repositories.UserRepository;
import com.aditya.electronic.store.services.UserService;
import jakarta.transaction.UserTransaction;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
       User user = dtoToEnitity(userDto);
       user.setPassword(passwordEncoder.encode(user.getPassword()));

       Role role = new Role();
       role.setRoleId(UUID.randomUUID().toString());
       role.setName("ROLE_"+AppConstants.ROLE_NORMAL);
       Role roleNormal = roleRepository.findByName("ROLE_"+AppConstants.ROLE_NORMAL).orElse(role);
       user.setRoles(List.of(roleNormal));
       User savedUser = userRepository.save(user);
       UserDto userDto1 = entityToDto(savedUser);
        return userDto1;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found With This userId !!!"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());

        User updatedUser =  userRepository.save(user);

        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found With This userId !!!"));

        String fullPath = imagePath+user.getImageName();
        try
        {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
       catch(NoSuchFileException ex)
       {
           logger.info("User Image Not Found in Folder ");
           ex.printStackTrace();
       }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
       // Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortDir).descending()) : (Sort.by(sortDir).ascending());
        Sort  sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<User> pages = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(pages,UserDto.class);
        return response;
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
