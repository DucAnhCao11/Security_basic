package com.example.demo.DTO.Request;


import com.example.demo.exceptions.ErrorCode;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    private Integer idRole;

    @Size(min = 5, max = 50,  message = "USERNAME_ERROR")
    private String userName;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=.*[a-zA-Z]).{8,}$", message = "PASSWORD_ERROR")
    private String password;

}
