package com.example.demo.service;


import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.RoleRepositry;
import com.example.demo.repo.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private RoleRepositry roleRepositry;

    public User registerNewUser(User user) {
   return userRepositry.save(user);
    }
    public void initRoleUser() {
        Role adminRole = new Role();
        if (!roleRepositry.existsById("admin")) {

            adminRole.setId("admin");
            adminRole.setRolesDescription("admin role");
            roleRepositry.save(adminRole);
        }
        if (!roleRepositry.existsById("user")) {
            Role userRole = new Role();
            userRole.setId("user");
            userRole.setRolesDescription("user role");
            roleRepositry.save(adminRole);
        }
        if (!userRepositry.existsById("admin123")) {
            User user = new User();
            user.setUserName("admin123");
            user.setUserPassword("AS@1234");
            user.setUserFirstName("vimukthi");
            user.setUserLastName("ko");

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            user.setRole(adminRoles);
            userRepositry.save(user);
        }
    }
    }


