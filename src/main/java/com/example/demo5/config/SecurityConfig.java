package com.example.demo5.config;

import com.example.demo5.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  
    @Autowired     
    private AccountService accountService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                // USER và ADMIN đều được xem danh sách
                .requestMatchers("/products").hasAnyRole("USER", "ADMIN")
                // Chỉ ADMIN mới được thêm, sửa, xóa
                .requestMatchers("/products/**").hasRole("ADMIN")
                // Cho phép tất cả mọi người truy cập vào trang login
                .requestMatchers("/login").permitAll()
                // Các request khác bắt buộc đăng nhập
                .anyRequest().authenticated()
        )
        // Cấu hình trang đăng nhập tùy chỉnh
        .formLogin(form -> form
                .loginPage("/login") // Chỉ định đường dẫn tới trang đăng nhập
                .defaultSuccessUrl("/products", true)
                .permitAll()
        )
        // Cấu hình đăng xuất
        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Đăng xuất xong quay về trang login
                .permitAll()
        );

        return http.build();
    }
}