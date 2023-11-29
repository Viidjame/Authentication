package com.example.service.Implementation;


import com.example.enumeration.Role;
import com.example.exception.CustomExceptionClass;
import com.example.exception.NotFoundExceptionClass;
import com.example.model.entity.User;
import com.example.model.request.UserRequest;
import com.example.model.response.UserDto;
import com.example.model.update.UserUpdate;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImp implements UserService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    public UserServiceImp(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    public User createUser(UserRequest userRequest) {

        UsersResource userResource = keycloak.realm(realm).users();

        CredentialRepresentation credentialRepresentation = credentialRepresentation(userRequest.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(userRequest.getEmail());
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setFirstName(userRequest.getFirstName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.singleAttribute("createdAt", LocalDateTime.now().toString().formatted("yyyy-MM-dd HH:mm-ss"));
        userRepresentation.singleAttribute("lastModified", LocalDateTime.now().toString().formatted("yyyy-MM-dd HH:mm-ss"));

        String rolesAttribute = userRequest.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.joining(","));

        userRepresentation.singleAttribute("roles", rolesAttribute);
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        userResource.create(userRepresentation);

        credentialRepresentation(userRequest.getPassword());

        // Get user
        UserRepresentation user = keycloak.realm(realm).users().search(userRepresentation.getUsername()).get(0);
        return UserDto.todo(user);

    }

    @Override
    public List<User> getAllUser() {

        UsersResource usersResource = keycloak.realm(realm).users();
        List<UserRepresentation> users = usersResource.list();
        return users.stream().map(UserDto::todo).toList();

    }


    @Override
    public User getUserById(UUID id) {
        try{
            UserRepresentation user = keycloak.realm(realm).users().get(String.valueOf(id)).toRepresentation();
            return UserDto.todo(user);
        }catch (Exception e){
            throw new NotFoundExceptionClass("User not found");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try{
            UserRepresentation user = keycloak.realm(realm).users().searchByEmail(email,true).get(0);
            return UserDto.todo(user);
        }catch (Exception e){
            throw new CustomExceptionClass("Bad Request");
        }

    }

    @Override
    public User updateUserById(UUID id, UserUpdate userUpdate) {

            UsersResource userResource = keycloak.realm(realm).users();
            User user = getUserById(id);
            UserRepresentation userRepresentation = keycloak.realm(realm).users().get(String.valueOf(id)).toRepresentation();
            userRepresentation.setEmail(userUpdate.getEmail());
            userRepresentation.setLastName(userUpdate.getLastName());
            userRepresentation.setFirstName(userUpdate.getFirstName());
            userRepresentation.singleAttribute("createdAt", user.getCreatedAt().toString());
            userRepresentation.singleAttribute("lastModified", String.valueOf(LocalDateTime.now()));

            userResource.get(String.valueOf(id)).update(userRepresentation);
            return UserDto.todo(userRepresentation);

    }

    private static CredentialRepresentation credentialRepresentation(String password){

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    @Override
    public User getUserByUsername(String username)  {
        try{
            UserRepresentation user = keycloak.realm(realm).users().searchByUsername(username,true).get(0);
            return UserDto.todo(user);
        }catch (Exception e){
            throw new NotFoundExceptionClass("User not found");
        }

    }

    @Override
    public void deleteUserById(UUID id) {
        try {
            UserRepresentation user = keycloak.realm(realm).users().get(String.valueOf(id)).toRepresentation();
            if (user != null) {
                keycloak.realm(realm).users().get(String.valueOf(id)).remove();
            }

        } catch (Exception e) {
            throw new NotFoundExceptionClass("User not found");
        }
    }

}
