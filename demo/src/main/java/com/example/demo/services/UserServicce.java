package com.example.demo.services;

import com.example.demo.DTO.Request.UserCreationRequest;
import com.example.demo.DTO.Request.UserUpdateRequest;
import com.example.demo.DTO.Response.UserResponse;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.exceptions.APPException;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicce {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        Role role = roleRepository.findById(request.getIdRole())
                .orElseThrow(() -> new APPException(ErrorCode.ROLE_NOT_FOUND));

        if(userRepository.existsByUserName(request.getUserName())){
            throw new APPException(ErrorCode.OBJECT_EXISTS);
        }

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public UserResponse updateUser(Integer id,UserUpdateRequest rq) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new APPException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, rq);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(Integer id) {
        return userMapper.toUserResponse(userRepository.findById(id)
        .orElseThrow(() -> new APPException(ErrorCode.USER_NOT_FOUND)));
    }
}
