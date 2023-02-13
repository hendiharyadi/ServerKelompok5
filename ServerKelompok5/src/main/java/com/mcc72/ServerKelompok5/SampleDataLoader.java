/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5;

import com.github.javafaker.Faker;
import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.Privilege;
import com.mcc72.ServerKelompok5.models.entity.Role;
import com.mcc72.ServerKelompok5.repositories.PrivilegeRepository;
import com.mcc72.ServerKelompok5.repositories.RoleRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import com.mcc72.ServerKelompok5.services.EmployeeService;
import com.mcc72.ServerKelompok5.services.StockLeaveService;
import com.mcc72.ServerKelompok5.services.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hendi
 */
@AllArgsConstructor
@Component
public class SampleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserRepository userRepository;
    private final UserEntityService userEntityService;
    private final StockLeaveService stockLeaveService;
    private final EmployeeService employeeService;
    private final Faker faker;

    @Override
    public void run(String... args) throws Exception {


        if (roleRepository.findByName("ROLE_USER").isPresent()) {
            System.out.println("ROLE_USER data already exist.");
        } else {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(0, "ROLE_USER", null, null));
            roles.add(new Role(0, "ROLE_MANAGER", null, null));
            roles.add(new Role(0, "ROLE_ADMIN", null, null));
            roleRepository.saveAll(roles);
        }

        if (privilegeRepository.findByName("CREATE_USER").isPresent()) {
            System.out.println("CREATE_USER data already exist.");
        } else {
            List<Role> rolesUser = Arrays.asList(roleRepository.findByName("ROLE_USER").get());
            List<Role> rolesAdmin = Arrays.asList(roleRepository.findByName("ROLE_ADMIN").get());
            List<Role> rolesManager = Arrays.asList(roleRepository.findByName("ROLE_MANAGER").get());
            List<Privilege> privileges = new ArrayList<>();
            privileges.add(new Privilege(0, "CREATE_USER", rolesUser));
            privileges.add(new Privilege(0, "READ_USER", rolesUser));
            privileges.add(new Privilege(0, "UPDATE_USER", rolesUser));
            privileges.add(new Privilege(0, "DELETE_USER", rolesUser));

            privileges.add(new Privilege(0, "CREATE_MANAGER", rolesManager));
            privileges.add(new Privilege(0, "READ_MANAGER", rolesManager));
            privileges.add(new Privilege(0, "UPDATE_MANAGER", rolesManager));
            privileges.add(new Privilege(0, "DELETE_MANAGER", rolesManager));

            privileges.add(new Privilege(0, "CREATE_ADMIN", rolesAdmin));
            privileges.add(new Privilege(0, "READ_ADMIN", rolesAdmin));
            privileges.add(new Privilege(0, "UPDATE_ADMIN", rolesAdmin));
            privileges.add(new Privilege(0, "DELETE_ADMIN", rolesAdmin));
            privilegeRepository.saveAll(privileges);

        }

    }
}
