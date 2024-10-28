package com.itMentor.Task312.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String lastName;
    private String email;
    private List<String> roleNames;
}

