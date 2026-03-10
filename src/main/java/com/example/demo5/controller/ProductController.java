package com.example.demo5.controller;

import jakarta.validation.Valid;
import com.example.demo5.model.Product;
import com.example.demo5.service.CategoryService;
import com.example.demo5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách sản phẩm
    @GetMapping
    // public String listProducts(Model model) {
    //     List<Product> productList = productService.getAllProducts();
    //     model.addAttribute("products", productList);
    //     return "product/list"; // list.html trong templates/product
    // }

    public String listProducts(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Product> productList;
        
        // Nếu có từ khóa tìm kiếm thì gọi hàm search, ngược lại lấy tất cả
        if (keyword != null && !keyword.trim().isEmpty()) {
            productList = productService.searchProducts(keyword);
            model.addAttribute("keyword", keyword); // Giữ lại từ khóa trên ô tìm kiếm
        } else {
            productList = productService.getAllProducts();
        }
        
        model.addAttribute("products", productList);
        return "product/list"; 
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add"; // Trỏ đến file add.html trong templates/product
    }

    // Lưu sản phẩm (Có kiểm tra lỗi Validation)
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, 
                              BindingResult result, 
                              Model model) {
        
        // 1. Nếu nhập thiếu hoặc sai quy tắc (Có lỗi)
        if (result.hasErrors()) {
            // Bắt buộc phải load lại danh sách Category, nếu không dropdown sẽ bị trống và báo lỗi
            model.addAttribute("categories", categoryService.getAllCategories());
            
            // Kiểm tra xem người dùng đang ở form Add hay form Edit để trả về cho đúng
            if (product.getId() > 0) {
                return "product/edit"; // Nếu có ID tức là đang sửa
            } else {
                return "product/add";  // Nếu không có ID tức là đang thêm mới
            }
        }
        
        // 2. Nếu dữ liệu chuẩn xác -> Lưu vào Database
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/edit"; // Tự tạo file edit.html tương tự add.html
        }
        return "redirect:/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}