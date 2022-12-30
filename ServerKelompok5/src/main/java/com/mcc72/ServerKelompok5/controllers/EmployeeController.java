package com.mcc72.ServerKelompok5.controllers;


import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.services.EmployeeService;
import com.mcc72.ServerKelompok5.services.StockLeaveService;
import com.mcc72.ServerKelompok5.services.UserEntityService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("employee")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class EmployeeController {

    private EmployeeService employeeService;
    private UserEntityService userEntityService;
    private StockLeaveService stockLeaveService;
    
//    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap() {
        return employeeService.getAllMap();
    }
    
    @PostMapping
    public Employee insert(@RequestBody UserRegistrationDto employee) {
        Employee e = employeeService.insert(employee);
        userEntityService.insert(employee);
        stockLeaveService.create(employee);
        userEntityService.sendVerifyMail(employee);
        return e;
    }
    
    @GetMapping("/{id}")
    public Object getById(@PathVariable int id) {
        return employeeService.findById(id);
    }
    
//    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody UserRegistrationDto employee) {
        return employeeService.update(id, employee);
    }
    
//    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return employeeService.deleteById(id);
    }
}
