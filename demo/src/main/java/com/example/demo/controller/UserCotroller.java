package com.example.demo.controller;

import com.example.demo.DTO.Request.APIResponse;
import com.example.demo.DTO.Request.UserCreationRequest;
import com.example.demo.DTO.Request.UserUpdateRequest;
import com.example.demo.entities.User;
import com.example.demo.services.UserServicce;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserCotroller {
    @Autowired
    UserServicce userServicce;

    @PostMapping
    APIResponse<User> addUser(@RequestBody @Valid UserCreationRequest request) {
        APIResponse<User> apiResponse = new APIResponse<>();
        apiResponse.setData(userServicce.createUser(request));
        return apiResponse;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User>updateUser(@PathVariable int id
            ,@Valid @RequestBody UserUpdateRequest request) {

            User user = userServicce.updateUser(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
       try{
           userServicce.deleteUser(id);
           return "User deleted";
       }catch (Exception e) {
           return "User could not be deleted";
       }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = null;
        try{
            users = userServicce.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userServicce.getUserById(id);
    }
}
