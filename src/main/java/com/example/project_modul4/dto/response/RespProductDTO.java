package com.example.project_modul4.dto.response;

import com.example.project_modul4.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespProductDTO {
    private Integer productId ;
    private String productName ;
    private String description ;
    private Double price ;
    private String image;
    private Integer stock;
    private Boolean status ;
    private Category category ;
}