package com.example.project_modul4.controller;

import com.example.project_modul4.dto.request.ProductDTO;
import com.example.project_modul4.dto.response.RespProductDTO;
import com.example.project_modul4.model.entity.Category;
import com.example.project_modul4.service.CategoryService;
import com.example.project_modul4.service.ProductService;
import com.example.project_modul4.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PropertySource("classpath:config.properties")
@RequestMapping("/admin")
public class ProductController {
    @Value("${path}")
    private String pathUpload;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/product")
    public String index(Model model){
        List<RespProductDTO> productDTOList = productService.getAll();
        model.addAttribute("productDTOList",productDTOList);
        return "admin/product/index";
    }

    @GetMapping("/add-product")
    public String createProduct(Model model){
        ProductDTO productDTO=new ProductDTO();
        model.addAttribute("productDTO",productDTO);
        List<Category> categoryList=categoryService.getAll();
        model.addAttribute("categoryList",categoryList);
        return "admin/product/add";
    }

    @PostMapping("/add-product")
    public String postCreateProduct(@Valid @ModelAttribute("productDTO") RespProductDTO respProductDTO,
                                    BindingResult result,
                                    @RequestParam(name = "fileImage",required = false) MultipartFile multipartFile,
                                    Model model) {
        System.out.println(multipartFile);

        if (result.hasErrors()) {
            List<Category> categories = categoryService.getAll();
            model.addAttribute("category", categories);
            return "admin/product/add";
        }
        if (multipartFile.isEmpty()) {
            model.addAttribute("fileError", "Please select a file to upload.");
            List<Category> categories = categoryService.getAll();
            model.addAttribute("category", categories);
            return "admin/product/add";
        }
        try {
            // Upload the file
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(pathUpload + fileName);
            System.out.println(file);
            multipartFile.transferTo(file);
            respProductDTO.setImage(fileName);
            productService.add(respProductDTO);
            return "redirect:/admin/product";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable Integer id , Model model) {
        RespProductDTO respProductDTO = productService.findById(id) ;
        model.addAttribute("productDTO", respProductDTO);
        List<Category> list = categoryService.getAll();
        model.addAttribute("category", list);
        return "admin/product/edit";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@Valid @PathVariable Integer id ,
                                  @ModelAttribute("product") RespProductDTO respProductDTO,
                                  BindingResult result, Model model ,
                                  @RequestParam("image-Update")MultipartFile multipartFile){
        if ( result.hasErrors()) {
            List<Category> categories = categoryService.getAll();
            model.addAttribute("category", categories);
            return "admin/product/edit";
        }

        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(pathUpload + fileName);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            respProductDTO.setImage(fileName);
        } else {
            RespProductDTO respProductDTO1 = productService.findById(id);
            respProductDTO.setImage(respProductDTO1.getImage());
        }
        productService.update(respProductDTO,id);
        return "redirect:/admin/product";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try {
            // Gọi phương thức xóa từ service (hoặc DAO)
            productService.delete(id);
            // Nếu xóa thành công, thêm thông báo thành công vào redirectAttributes
            redirectAttributes.addFlashAttribute("successMessage", "product deleted successfully");
        } catch (Exception e) {
            // Nếu có lỗi, thêm thông báo lỗi vào redirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product");
        }

        // Chuyển hướng về trang danh sách các product
        return "redirect:/admin/product";
    }
}
