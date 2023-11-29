package com.example.controller;
import com.example.model.entity.User;
import com.example.model.request.UserRequest;
import com.example.model.response.ApiResponse;
import com.example.model.update.UserUpdate;
import com.example.service.Implementation.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class AuthenticationController {
    private final UserServiceImp userServiceImp;

    public AuthenticationController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserRequest userRequest){
        ApiResponse<User> apiResponse = ApiResponse.<User>builder()
                .message("You have successfully created new user")
                .status(HttpStatus.CREATED)
                .payload(userServiceImp.createUser(userRequest))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUser(){
        ApiResponse<List<User>> apiResponse = ApiResponse.<List<User>>builder()
                .message("You have successfully fetched users")
                .status(HttpStatus.OK)
                .payload(userServiceImp.getAllUser())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable UUID id){
        ApiResponse<User> apiResponse = ApiResponse.<User>builder()
                .message("You have successfully created new user")
                .status(HttpStatus.OK)
                .payload(userServiceImp.getUserById(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/username")
    public ResponseEntity<ApiResponse<User>> getUserByUsername(@RequestParam String username){
        ApiResponse<User> apiResponse = ApiResponse.<User>builder()
                .message("You have successfully get user by username")
                .status(HttpStatus.OK)
                .payload(userServiceImp.getUserByUsername(username))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponse<User>> getUserByEmail(@RequestParam String email){
        ApiResponse<User> apiResponse = ApiResponse.<User>builder()
                .message("You have successfully fetch user by email")
                .status(HttpStatus.OK)
                .payload(userServiceImp.getUserByEmail(email))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable UUID id){
        userServiceImp.deleteUserById(id);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .message("You have successfully deleted user by id")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUserById(@PathVariable UUID id, @RequestBody UserUpdate userUpdate){
        ApiResponse<User> apiResponse = ApiResponse.<User>builder()
                .message("You have successfully updated user by id")
                .status(HttpStatus.OK)
                .payload(userServiceImp.updateUserById(id,userUpdate))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }






}
