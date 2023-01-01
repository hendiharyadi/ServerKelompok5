/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.EmployeeProjectDto;
import com.mcc72.ServerKelompok5.models.dto.StockResponse;
import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.*;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mcc72.ServerKelompok5.repositories.ProjectRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository er;
    private ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Employee> findAll() {
        if (er.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data available.");
        }
        for (Employee employee : er.findAll()) {
            employee.getFirst_name();
            employee.getLast_name();
            employee.getEmail();
            employee.getPhone_number();
        }
        return er.findAll();
    }

    public List<Map<String, Object>> getAllMap() {
        return er.findAll().stream().map(employee -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", employee.getId());
            m.put("first_name", employee.getFirst_name());
            m.put("last_name", employee.getLast_name());
            m.put("email", employee.getEmail());
            m.put("phone_number", employee.getPhone_number());
            m.put("user", employee.getUser());
            m.put("manager", employee.getManager());
            m.put("managers", employee.getManagers());
            m.put("employeeProject", employee.getProjects());
            m.put("stockLeave", employee.getStockLeave());
            m.put("overtimes", employee.getOvertimes());
            m.put("permissions", employee.getPermissions());
            m.put("projects", employee.getProjects());
            return m;
        }).collect(Collectors.toList());
    }

    public Object findById(Integer id) {
        if (!er.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist.");
        }
        Employee employee = er.findById(id).get();
        Map<String, Object> m = new HashMap<>();
        m.put("id", employee.getId());
        m.put("first_name", employee.getFirst_name());
        m.put("last_name", employee.getLast_name());
        m.put("email", employee.getEmail());
        m.put("phone_number", employee.getPhone_number());
        m.put("user", employee.getUser());
        m.put("manager", employee.getManager());
        m.put("managers", employee.getManagers());
        m.put("employeeProject", employee.getProjects());
        m.put("stockLeave", employee.getStockLeave());
        m.put("overtimes", employee.getOvertimes());
        m.put("permissions", employee.getPermissions());
        m.put("projects", employee.getProjects());
        return m;
    }

    public Employee insert(UserRegistrationDto e) {
        if (er.findByEmail(e.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FOUND, "Data is exist!!!");
        }
        Employee employee = new Employee();
        employee.setFirst_name(e.getFirst_name());
        employee.setLast_name(e.getLast_name());
        employee.setEmail(e.getEmail());
        employee.setPhone_number(e.getPhone_number());
        if (e.getManager_id() != null){
            employee.setManager(er.findById(e.getManager_id()).get());
        }
        return er.save(employee);
    } 

    public Employee update(Integer id, UserRegistrationDto e) {
        findById(id);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirst_name(e.getFirst_name());
        employee.setLast_name(e.getLast_name());
        employee.setEmail(e.getEmail());
        employee.setPhone_number(e.getPhone_number());
        if (e.getManager_id() != null){
            employee.setManager(er.findById(e.getManager_id()).get());
        }
        return er.save(employee);
    }

    public String deleteById(Integer id) {
        Employee e = er.findById(id).get();
        er.delete(e);
        return "Success";
    }

    public List<Employee> findMyStaff(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity manager = userRepository.findByUsername(auth.getName()).get();
        return manager.getEmployee().getManagers();
    }

    public List<Permission> findMyStaffPermission(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity manager = userRepository.findByUsername(auth.getName()).get();
        return manager.getEmployee().getPermissions();
    }

    public Object addEmployeeToProject( EmployeeProjectDto empDto){
        Project project = projectRepository.findById(empDto.getProject_id()).get();
        Employee employee = er.findById(empDto.getEmployee_id()).get();
        /*employee.setEmployeeProject(Collections.singletonList(project));*/
        project.setEmployeeProject(Collections.singletonList(employee));
        projectRepository.save(project);
        Map<String, Object> map = new HashMap<>();
        map.put("employee", employee);
        map.put("employee project", employee.getEmployeeProject());
        return map;
    }

    public StockResponse getUserStockLeave(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity manager = userRepository.findByUsername(auth.getName()).get();
        StockResponse stockResponse = new StockResponse();
        stockResponse.setStock_available(manager.getEmployee().getStockLeave().getStock_available());
        return stockResponse;
    }
}
