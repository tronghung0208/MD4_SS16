package com.example.project_modul4.service;

import com.example.project_modul4.dto.response.RespProductDTO;
import com.example.project_modul4.model.dao.ProductDAO;
import com.example.project_modul4.model.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO productDAO;
    private ModelMapper modelMapper;

    @Override
    public List<RespProductDTO> getAll() {
        modelMapper = new ModelMapper();
        List<Product> list = productDAO.getAll();
        List<RespProductDTO> productDTOList = new ArrayList<>();
        productDTOList = list.stream()
                .map(item -> modelMapper.map(item, RespProductDTO.class))
                .collect(Collectors.toList());
        return productDTOList;
    }


    @Override
    public Boolean add(RespProductDTO respProductDTO) {
        modelMapper = new ModelMapper();
        return productDAO.add(modelMapper.map(respProductDTO, Product.class));
    }

    @Override
    public Boolean update(RespProductDTO respProductDTO, Integer id) {
        modelMapper = new ModelMapper();
        return productDAO.update(modelMapper.map(respProductDTO, Product.class), id);
    }

    @Override
    public RespProductDTO findById(Integer id) {
        modelMapper = new ModelMapper();
        Product product = productDAO.findById(id);
        return modelMapper.map(product, RespProductDTO.class);
    }

    @Override
    public void delete(Integer id) {
        productDAO.delete(id);
    }
}
