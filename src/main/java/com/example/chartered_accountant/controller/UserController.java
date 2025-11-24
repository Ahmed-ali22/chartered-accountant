package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.user.UserResponseDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.util.mapper.UserMapper;
import com.example.chartered_accountant.util.security.CustomUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.service.user.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService ) {
        log.info("User Service Injected");
        this.userService = userService;
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(@AuthenticationPrincipal CustomUserPrincipal principal
            , @Valid @RequestBody UserRequestDto userDto) {
       User user =  userService.update(principal.getUserId(),userDto);
            return ResponseEntity.ok(UserMapper.toUserResponseDto(user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUserPrincipal principal) {
        userService.deleteById(principal.getUserId());
        return  ResponseEntity.ok("User Deleted Successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal CustomUserPrincipal principal) {
        User user = userService.findById(principal.getUserId());
        return ResponseEntity.ok(UserMapper.toUserResponseDto(user));
    }

}
