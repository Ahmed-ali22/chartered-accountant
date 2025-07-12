package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.model.dto.user.UserDto;
import com.example.chartered_accountant.model.dto.user.UserUpdateDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.UserRepo;
import com.example.chartered_accountant.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public void update(UserUpdateDto userDto) {
        User user = userRepo.findByEmail(userDto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("User Not Found"));
        userRepo.save(UserMapper.updateEntityFromDto(userDto,user));
        log.info("User Email : {} successfully Updated  ", userDto.getEmail());
    }

    @Override
    public void deleteByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("User Not Found"));
        userRepo.delete(user);
        log.info("User Successfully Deleted");
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
        log.info("All Users Successfully Deleted");
    }

    @Override
    public UserDto  findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException ("User not found"));
        log.info("User successfully Found  ");
        return  UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepo.findAll();
        if(userList.isEmpty()) {
            throw new IllegalArgumentException ("Users not found");
        }
        log.info("All Users Successfully Found");
        return  userList.stream().map(UserMapper::toDto).toList();
    }
}
