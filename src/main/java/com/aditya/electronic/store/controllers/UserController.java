package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.ApiResponseMessage;
import com.aditya.electronic.store.dtos.ImageResponse;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.services.FileService;
import com.aditya.electronic.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId,@Valid @RequestBody UserDto userDto){
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
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir
    )
    {
        PageableResponse<UserDto> users = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(users, HttpStatus.OK);
        //return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
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
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage")MultipartFile file, @PathVariable String userId) throws IOException {
       String imageName =  fileService.uploadFile(file,imageUploadPath);
       UserDto user = userService.getUserById(userId);
       user.setImageName(imageName);
       UserDto userDto = userService.updateUser(user,userId);
       ImageResponse imageResponse = ImageResponse.builder()
               .imageName(imageName)
               .success(true)
               .status(HttpStatus.CREATED)
               .build();
       return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
}
