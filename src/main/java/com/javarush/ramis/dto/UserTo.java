package com.javarush.ramis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTo {
    private Long id;
    private String login;
    private String password;
    private Role role;
}
