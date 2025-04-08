package com.example.Demo_E_Commerce.model.product;

import lombok.Data;

@Data
public class ProductDto {
    int id ;
    String name;
    float price;
    int category_id;
}
