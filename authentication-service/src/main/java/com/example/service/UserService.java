package com.example.service;

import com.example.model.entity.User;
import com.example.model.request.UserRequest;
import com.example.model.update.UserUpdate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public interface UserService {

    User createUser(UserRequest userRequest);

    List<User>  getAllUser();

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void deleteUserById(UUID id);

    User updateUserById(UUID id, UserUpdate userUpdate);
}
