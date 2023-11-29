package com.example.model.entity;

import com.example.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    private List<Role> role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm-ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm-ss")
    private LocalDateTime lastModified;


}