package com.example.demo.controller;

import com.example.demo.DTO.Request.APIResponse;
import com.example.demo.DTO.Request.UserCreationRequest;
import com.example.demo.DTO.Request.UserUpdateRequest;
import com.example.demo.DTO.Response.UserResponse;
import com.example.demo.services.UserServicce;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserCotroller {
    private static final Logger log = LoggerFactory.getLogger(UserCotroller.class);
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
    public APIResponse<List<UserResponse>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("UserName:{}",authentication.getName());
        log.info("Roles:{}", authentication.getAuthorities());
        return APIResponse.<List<UserResponse>>builder()
                .data(userServicce.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse<UserResponse> getUserById(@PathVariable("id") int id) {
        APIResponse<UserResponse> apiResponse = new APIResponse<>();
        apiResponse.setData(userServicce.getUserById(id));
        return apiResponse;
    }

    @GetMapping("/myInfo")
    public APIResponse<UserResponse> getMyInfo() {
       return APIResponse.<UserResponse>builder()
               .data(userServicce.getMyUserName())
               .build();
    }
}
