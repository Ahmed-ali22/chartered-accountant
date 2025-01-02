package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.model.dto.UserDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.UserRepo;
import com.example.chartered_accountant.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private  final  UserRepo userRepo;

    @Autowired
    UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
        log.info(" User Repository injected successfully ");
    }

    @Override
    public void save(UserDto userDto) {
        if(userRepo.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException(" Email already in use");
        }
        User user = UserMapper.toEntity(userDto);
        userRepo.save(user);
        log.info("New user added successfully");

    }

    @Override
    public void update(UserDto userDto) {

    }

    @Override
    public void deleteByEmail() {

    }

    @Override
    public void findByEmail() {

    }
}
