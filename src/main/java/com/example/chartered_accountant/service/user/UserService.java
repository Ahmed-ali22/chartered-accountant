package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.model.dto.user.UserDto;
import com.example.chartered_accountant.model.dto.user.UserUpdateDto;


import java.util.List;

public interface UserService {
    void save(UserDto userDto);

    void update( UserUpdateDto userDto);

    void deleteByEmail(String email);

    void deleteAll();

    UserDto findByEmail(String email);

    List<UserDto> findAll();
}
