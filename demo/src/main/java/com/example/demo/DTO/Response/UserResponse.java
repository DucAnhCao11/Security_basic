package com.example.demo.DTO.Response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;

    private String userName;

    private String maRole;

    private Integer trangThai;
}
