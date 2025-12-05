package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.model.dto.user.PasswordUpdateDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.model.dto.user.UserResponseDto;
import com.example.chartered_accountant.model.dto.user.UserUpdateDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.UserRepo;
import com.example.chartered_accountant.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private  final  UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        log.info(" User Repository injected successfully ");
    }

    @Override
    public void save(UserRequestDto userRequestDto) {
        if(userRepo.existsByEmail(userRequestDto.getEmail())) {
            throw new UserException(
                    409, "userConflict", "User already exists with email: "+ userRequestDto.getEmail()
            );
        }
        User user = UserMapper.toUserEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepo.save(user);
        log.info("New User Added Successfully");

    }

    @Override
    public User update(UUID id , UserUpdateDto userUpdateDto) {
        if(userRepo.existsByEmailAndIdNot(userUpdateDto.getEmail() , id)) {
            throw new UserException(
                    409, "userConflict", "Email already in use :  " + userUpdateDto.getEmail()
            );
        }
        User user = userRepo.findById(id)
                .orElseThrow(()-> new UserException(
                        404, "userNotFound", "User not found with email: "+ userUpdateDto.getEmail()
                ));
        User updatedUser = UserMapper.updateEntityFromDto(userUpdateDto,user);
        userRepo.save(updatedUser);
        log.info("User Email : {} successfully Updated  ", userUpdateDto.getEmail());
        return updatedUser;
    }

    @Override
    public void deleteById(UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(()-> new UserException(
                        404, "userNotFound", "User not found with ID: " + id
                ));
        userRepo.delete(user);
        log.info("User Successfully Deleted with Email:{}", user.getEmail());
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
        log.info("All Users Successfully Deleted");
    }

    @Override
    public User findById(UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(()-> new UserException(
                404, "userNotFound", "User not found with ID: " + id
        ));
        log.info("User successfully Found ");
        return  user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepo.findAll();
        if(userList.isEmpty()) {
            throw new UserException(
                    404, "noUsersFound", "No users exist in the system"
            );
        }
        log.info("All Users Successfully Found");
        return  userList;
    }

    @Override
    public void updatePassword(UUID id, PasswordUpdateDto passwordUpdateDto) {
        User user = userRepo.findById(id).orElseThrow(()->new UserException(
                404, "userNotFound" , "User not found with ID : " + id
        ));
        if (passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
            userRepo.save(user);
            log.info("Password of User Email {} , Updated Successfully ",user.getEmail());
        } else {
            throw new UserException(400, "invalidPassword", "Old password is incorrect");
        }
    }
}
