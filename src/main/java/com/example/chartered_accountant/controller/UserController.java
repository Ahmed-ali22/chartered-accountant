package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.user.UserResponseDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.util.mapper.AdminMapper;
import com.example.chartered_accountant.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.service.user.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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

    // save API in the account controller
    @PostMapping()
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
            userService.save(userRequestDto);
            return ResponseEntity.ok("New User Created Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id
            , @Valid @RequestBody UserRequestDto userDto) {
       User user =  userService.update(id,userDto);
            return ResponseEntity.ok(UserMapper.toUserResponseDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return  ResponseEntity.ok("User Deleted Successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toUserResponseDto(user));
    }

}
