package com.example.demo5.config; // Đổi package cho đúng với dự án của bạn

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Đánh dấu đây là class cấu hình
public class WebConfig implements WebMvcConfigurer {
    @Override // Ghi đè phương thức để thêm Resource Handlers
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình: Khi gặp đường dẫn /images/** thì tìm trong thư mục src/...
        // Lưu ý: Dấu / ở cuối đường dẫn là bắt buộc
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/resources/static/images/");
    }
}