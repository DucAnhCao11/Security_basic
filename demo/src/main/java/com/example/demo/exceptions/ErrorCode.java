package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorzed error"),
    ENUM_INVALID_KEY(5555, "enum invalid key"),
    OBJECT_EXISTS(1001, "Object already exists"),
    USER_NOT_FOUND(1002, "user not found"),
    ROLE_NOT_FOUND(1002, "user not found"),
    USERNAME_ERROR(1003, "Username must be least 5 characters and most 50 characters"),
    PASSWORD_ERROR(1004,
            "Password must start with a capital letter and must contain special characters " +
                    "and Username must be least 5 characters"),
    USER_NOT_EXISTS(1005, "user not exists"),
    PASSWORD_IS_INCORRECT(1006, "Password is incorrect"),
    ;

    private int code;
    private String message;
}
