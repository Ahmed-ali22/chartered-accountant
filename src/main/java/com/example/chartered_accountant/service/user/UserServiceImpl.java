package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.model.dto.user.UserResponseDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.UserRepo;
import com.example.chartered_accountant.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


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
    public void save(UserRequestDto userRequestDto) {
        if(userRepo.existsByEmail(userRequestDto.getEmail())) {
            throw new UserException(
                    409, "userConflict", "User already exists with email: "+ userRequestDto.getEmail()
            );
        }
        User user = UserMapper.toUserEntity(userRequestDto);
        userRepo.save(user);
        log.info("New User Added Successfully");

    }

    @Override
    public User update(UUID id , UserRequestDto userRequestDto) {
        if(userRepo.existsByEmailAndIdNot(userRequestDto.getEmail() , id)) {
            throw new UserException(
                    409, "userConflict", "Email already in use :  " + userRequestDto.getEmail()
            );
        }
        User user = userRepo.findById(id)
                .orElseThrow(()-> new UserException(
                        404, "userNotFound", "User not found with email: "+ userRequestDto.getEmail()
                ));
        User updatedUser = UserMapper.updateEntityFromDto(userRequestDto,user);
        userRepo.save(updatedUser);
        log.info("User Email : {} successfully Updated  ", userRequestDto.getEmail());
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
}
