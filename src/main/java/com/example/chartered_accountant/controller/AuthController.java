package com.example.chartered_accountant.controller;


import com.example.chartered_accountant.model.dto.Admin.AdminRequestDto;
import com.example.chartered_accountant.model.dto.Admin.AdminResponseDto;
import com.example.chartered_accountant.model.dto.Auth.AuthRequestDto;
import com.example.chartered_accountant.model.dto.Auth.AuthResponseDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.service.admin.AdminService;
import com.example.chartered_accountant.service.auth.AuthService;
import com.example.chartered_accountant.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final AdminService adminService;

    @Autowired
    public AuthController(UserService userService, AuthService authService, AdminService adminService) {
        this.userService = userService;
        this.authService = authService;
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.save(userRequestDto);
        return ResponseEntity.ok("New User Created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.login(authRequestDto));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> adminRegister(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        adminService.save(adminRequestDto);
        return ResponseEntity.ok("New Admin Created Successfully");
    }
}
