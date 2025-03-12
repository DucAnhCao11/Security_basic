package com.example.demo.DTO.Response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;

    private Integer idRole;

    private String userName;

    private String password;

    private Integer trangThai;
}
