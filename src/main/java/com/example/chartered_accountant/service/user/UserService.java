package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.model.dto.user.PasswordUpdateDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.model.dto.user.UserUpdateDto;
import com.example.chartered_accountant.model.entity.User;


import java.util.List;
import java.util.UUID;

public interface UserService {
    void save(UserRequestDto userRequestDto);

    User update(UUID id , UserUpdateDto userUpdateDto);

    void deleteById(UUID id);

    void deleteAll();

    User findById(UUID id);

    List<User> findAll();
    void  updatePassword(UUID id,PasswordUpdateDto passwordUpdateDto);
}
