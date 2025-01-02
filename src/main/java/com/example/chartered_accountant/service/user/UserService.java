package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.model.dto.UserDto;

public interface UserService {
    void save(UserDto userDto);

    void update(UserDto userDto);

    void deleteByEmail();

    void findByEmail();
}
