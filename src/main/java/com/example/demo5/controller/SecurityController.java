package com.example.demo5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Nhớ import thư viện này
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    
    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String login(Model model) {
        // Đánh dấu đây là trang đăng nhập để Layout nhận biết
        model.addAttribute("isLoginPage", true);
        return "user/login"; 
    }
}