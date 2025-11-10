package com.example.chartered_accountant.controller.account;


import com.example.chartered_accountant.model.dto.Admin.AdminRequestDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.service.admin.AdminService;
import com.example.chartered_accountant.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AccountController {
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AccountController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            userService.save(userRequestDto);
            return ResponseEntity.ok("New user is registered");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("admin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        try {
            adminService.save(adminRequestDto);
            return ResponseEntity.ok("New Admin is registered");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
