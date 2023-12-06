package com.example.project_modul4.controller;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.model.entity.Category;
import com.example.project_modul4.service.CategoryService;
import com.example.project_modul4.service.UserAdminService;
import com.example.project_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    UserAdminService userAdminService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping({"/", ""})
    public String admin() {
        return "admin/admin-home";
    }

    @RequestMapping("/user_manager")
    public String userManager(Model model) {
        List<ResponseUserLoginDTO> listUser = userService.listUser();
        model.addAttribute("listUser", listUser);
        return "admin/user_manager/index-user";
    }

    @RequestMapping("/update_status/{id}")
    public String updateStatus(@PathVariable("id") Integer id){
       userAdminService.updateStatus(id);
        return "redirect:/admin/user_manager";
    }


    @GetMapping("/category")
    public String categoryGetAll(Model model) {
        List<Category> categoryList=categoryService.getAll();
        model.addAttribute("categoryList",categoryList);
        return "admin/category/index";
    }
    @GetMapping("/add-category")
    public String addCategory(Model model) {
        Category category= new Category();
        model.addAttribute("category",category);
        return "admin/category/add";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") Category category, Model model) {
        // Kiểm tra và xử lý dữ liệu đầu vào
        if (!category.getCategoryName().isEmpty()) {
            Boolean isCheckAddCategory = categoryService.add(category);
            if (isCheckAddCategory) {
                // Nếu thành công, chuyển hướng đến trang chi tiết của danh mục vừa thêm mới
                return "redirect:/admin/category";
            } else {
                // Nếu không thành công, giữ người dùng ở trang hiện tại và hiển thị thông báo lỗi
                model.addAttribute("error", "Không thể thêm mới danh mục.");
                return "admin/category/add";
            }
        } else {
            // Nếu dữ liệu không hợp lệ, giữ người dùng ở trang hiện tại và hiển thị thông báo lỗi
            model.addAttribute("error", "Dữ liệu đầu vào không hợp lệ.");
            return "admin/category/add";
        }
    }

    @GetMapping("/edit-category/{categoryId}")
    public String editCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        // Thực hiện truy vấn cơ sở dữ liệu hoặc nguồn dữ liệu khác để lấy thông tin category cần chỉnh sửa
        Category categoryToEdit = categoryService.findById(categoryId);

        // Kiểm tra xem category có tồn tại không
        if (categoryToEdit == null) {
            // Xử lý khi không tìm thấy category
            return "redirect:/category"; // Chẳng hạn, chuyển hướng về trang danh sách category
        }

        // Đưa dữ liệu vào model để hiển thị trên trang chỉnh sửa
        model.addAttribute("category", categoryToEdit);

        // Trả về tên trang chỉnh sửa
        return "admin/category/edit";
    }
    @PostMapping("/update-category")
    public String updateCategory(@ModelAttribute("category") Category updatedCategory) {
        // Kiểm tra xem category có tồn tại không
        Category existingCategory = categoryService.findById(updatedCategory.getCategoryId());

        if (existingCategory == null) {
            // Xử lý khi không tìm thấy category
            return "redirect:/admin/category";
        }
        // Cập nhật thông tin category từ dữ liệu mới
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setStatus(updatedCategory.getStatus());
        // Gọi service để lưu thông tin category đã cập nhật vào cơ sở dữ liệu
        categoryService.update(updatedCategory,updatedCategory.getCategoryId());

        // Chuyển hướng về trang danh sách hoặc trang chi tiết category
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Integer categoryId, RedirectAttributes redirectAttributes) {
        try {
            // Gọi phương thức xóa từ service (hoặc DAO)
            categoryService.delete(categoryId);

            // Nếu xóa thành công, thêm thông báo thành công vào redirectAttributes
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // hoặc xử lý exception theo yêu cầu

            // Nếu có lỗi, thêm thông báo lỗi vào redirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting category");
        }

        // Chuyển hướng về trang danh sách các category
        return "redirect:/admin/category";
    }





}
