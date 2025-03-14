package com.example.demo.mapper;

import com.example.demo.DTO.Request.UserCreationRequest;
import com.example.demo.DTO.Request.UserUpdateRequest;
import com.example.demo.DTO.Response.UserResponse;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "trangThai", constant = "1")
    User toUser(UserCreationRequest request);

    @Mapping(source = "role.maRole", target = "maRole")
    UserResponse toUserResponse(User user);

//    @Mapping(source = "role.maRole", target = "maRole")
//    List<UserResponse> toUserResponseList(List<User> users);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
