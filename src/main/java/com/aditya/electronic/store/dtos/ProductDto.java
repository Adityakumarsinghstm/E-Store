package com.aditya.electronic.store.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private String id;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

    private String productImageName;

    @JsonIgnore
    private CategoryDto category;
}
