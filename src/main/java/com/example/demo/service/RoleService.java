package com.example.demo.service;


import com.example.demo.entity.Role;
import com.example.demo.repo.RoleRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RoleService {
    @Autowired
    private RoleRepositry roleRepo;
    public Role createRole(Role role){
        return roleRepo.save(role);

    }

}
