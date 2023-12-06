package com.example.project_modul4.dto.request;

import com.example.project_modul4.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO {
    @NotEmpty(message = "Tên sản phẩm không được để rỗng")
    private String productName ;
    @NotNull(message = "Danh mục sản phẩm không được để rỗng")
    private Category category ;
    private String image;
    private Double price ;
    private String description ;
    private Integer stock;
    private Boolean status ;
}
