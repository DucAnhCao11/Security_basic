package com.example.demo.services;

import com.example.demo.DTO.Request.UserCreationRequest;
import com.example.demo.DTO.Request.UserUpdateRequest;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.exceptions.APPException;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicce {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User createUser(UserCreationRequest request) {
        Role role = roleRepository.findById(request.getIdRole())
                .orElseThrow(() -> new APPException(ErrorCode.ROLE_NOT_FOUND));

        if(userRepository.existsByUserName(request.getUserName())){
            throw new APPException(ErrorCode.OBJECT_EXISTS);
        }

        User user = new User();
        user.setRole(role);
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setTrangThai(1);

        return userRepository.save(user);

    }

    public User updateUser(Integer id,UserUpdateRequest rq) {
        User user = getUserById(id);
        user.setPassword(rq.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new APPException(ErrorCode.USER_NOT_FOUND));
        return user;
    }
}
