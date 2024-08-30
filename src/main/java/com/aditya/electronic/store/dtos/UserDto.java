package com.aditya.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min = 2, max = 15,message = "Invalid name !!")
    private String name;
    @Email(message = "Invalid Email !!")
    @NotBlank(message = "Email Required !!")
    private String email;
    @NotBlank(message = "Password Required !!")
    private String password;
    @Size(min = 2, max = 6, message = "Invalid Gender !!")
    private String gender;
    @NotBlank(message = "Write something about yourself ")
    private String about;
    private String imageName;
}
