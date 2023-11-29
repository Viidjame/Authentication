package com.example.model.response;
import com.example.enumeration.Role;
import com.example.model.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class UserDto {

    public static User todo(UserRepresentation userRepresentation){


        String role = userRepresentation.getAttributes().get("roles").get(0);
        List<Role> roles = getRoles(role);

        System.out.println("Role : "+roles);

        User user = new User();
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        user.setId(UUID.fromString(userRepresentation.getId()));
        user.setFirstName(userRepresentation.getFirstName());
        user.setLastName(userRepresentation.getLastName());
        user.setCreatedAt(LocalDateTime.parse(userRepresentation.getAttributes().get("createdAt").get(0)));
        user.setLastModified(LocalDateTime.parse(userRepresentation.getAttributes().get("lastModified").get(0)));
        user.setRole(roles);


        return user;
    }

    private static List<Role> getRoles(String rolesAttribute) {
        return Arrays.stream(rolesAttribute.split(","))
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }



}
