package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.user.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import com.example.chartered_accountant.model.dto.user.UserDto;
import com.example.chartered_accountant.service.user.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService ) {
        log.info("User Service Injected");
        this.userService = userService;
    }
    @PostMapping("save")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDto userDto) {
            userService.save(userDto);
            return ResponseEntity.ok("New user is Saved");
    }
    @PutMapping("update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateDto userDto) {
            userService.update(userDto);
            return ResponseEntity.ok("User Updated Successfully");
    }
    @DeleteMapping("deleteByEmail/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        userService.deleteByEmail(email);
        return  ResponseEntity.ok("User Deleted Successfully");
    }
    @DeleteMapping("deleteAll")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAll();
        return   ResponseEntity.ok("ALL Users Deleted Successfully");
    }
    @GetMapping("findByEmail/{email}")
    public UserDto findUserByEmail(@PathVariable String email) {
            return userService.findByEmail(email);
    }
    @GetMapping("findAll")
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }
}
