/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
            m.put("employeeId", employee.getId());
            m.put("employeeFirstName", employee.getFirst_name());
            m.put("employeeLastName", employee.getLast_name());
            m.put("employeeEmail", employee.getEmail());
            m.put("employeePhoneNumber", employee.getPhone_number());
            return m;
        }).collect(Collectors.toList());
    }

    public Employee findById(Integer id) {
        if (!er.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist.");
        }
        return er.findById(id).get();
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
        employee.setManagers(e.getEmployees().stream().map(managerId -> er.findById(managerId).get()).collect(Collectors.toList()));
        return er.save(employee); 
    } 

    public Employee update(UserRegistrationDto e) {
        if (er.findByEmail(e.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist");
        }
        Employee employee = new Employee();
        return er.save(employee);
    }

    public String deleteById(Integer id) {
        if (!er.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist");
        }
        Employee e = findById(id);
        er.deleteById(id);
        return "Delete for " + e.getFirst_name() + "success!!!";
    }

}
