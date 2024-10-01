package com.aditya.electronic.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {

    private String id;
    @NotBlank(message = "Title is required !!")
    @Size(min = 4,message = "title should be minimum 4 character !!")
    private String title;
    @NotBlank(message = "category description required !!")
    private String description;
    private String coverImage;
}

