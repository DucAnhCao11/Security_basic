package com.example.demo.controller;

import com.example.demo.DTO.Request.APIResponse;
import com.example.demo.DTO.Request.UserCreationRequest;
import com.example.demo.DTO.Request.UserUpdateRequest;
import com.example.demo.DTO.Response.UserResponse;
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
    APIResponse<UserResponse> addUser(@RequestBody @Valid UserCreationRequest request) {
        APIResponse<UserResponse> apiResponse = new APIResponse<>();
        apiResponse.setData(userServicce.createUser(request));
        return apiResponse;
    }

    @PutMapping("/update/{id}")
    public APIResponse<UserResponse>updateUser(@PathVariable int id
            ,@Valid @RequestBody UserUpdateRequest request) {
        APIResponse<UserResponse> apiResponse = new APIResponse<>();
        apiResponse.setData(userServicce.updateUser(id, request));
            return apiResponse;
    }
    @DeleteMapping("/delete/{id}")
    public APIResponse<String> deleteUser(@PathVariable int id) {
        APIResponse<String> apiResponse = new APIResponse<>();
       try{
           userServicce.deleteUser(id);
           apiResponse.setMessage("User deleted successfully");
           return apiResponse;
       }catch (Exception e) {
           apiResponse.setMessage("Error deleting user");
           return apiResponse;
       }
    }

    @GetMapping("/getAll")
    public APIResponse<List<User>> getAllUsers() {
        APIResponse<List<User>> apiResponse = new APIResponse<>();
        try{
            apiResponse.setData(userServicce.getAllUsers());
            return apiResponse;
        }catch (Exception e) {
            apiResponse.setData(null);
            return apiResponse;
        }
    }

    @GetMapping("/{id}")
    public APIResponse<UserResponse> getUserById(@PathVariable("id") int id) {
        APIResponse<UserResponse> apiResponse = new APIResponse<>();
        apiResponse.setData(userServicce.getUserById(id));
        return apiResponse;
    }
}
