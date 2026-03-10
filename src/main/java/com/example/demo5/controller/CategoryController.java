package com.example.demo5.controller;

import com.example.demo5.model.Category;
import com.example.demo5.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách danh mục
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    // Form thêm danh mục
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    // Lưu danh mục (Test Validation ở đây)
    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute("category") Category category, 
                               BindingResult result, 
                               Model model) {
        // Nếu có lỗi (để trống tên) -> Quay lại form
        if (result.hasErrors()) {
            return "category/add";
        }
        
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }
}