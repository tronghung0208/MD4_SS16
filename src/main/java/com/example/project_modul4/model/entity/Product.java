package com.example.project_modul4.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

private Integer productId;
private String productName;
private String description;
private Double price;
private String image;
private Integer stock;
private Boolean status;
private Category category;
}
