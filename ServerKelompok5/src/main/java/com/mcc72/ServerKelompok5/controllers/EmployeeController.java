package com.mcc72.ServerKelompok5.controllers;


import com.mcc72.ServerKelompok5.models.dto.EmployeeProjectDto;
import com.mcc72.ServerKelompok5.models.dto.StockResponse;
import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.services.EmployeeService;
import com.mcc72.ServerKelompok5.services.StockLeaveService;
import com.mcc72.ServerKelompok5.services.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;

@AllArgsConstructor
@RestController
@RequestMapping("employee")
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_USER')")
public class EmployeeController {

    private EmployeeService employeeService;
    private UserEntityService userEntityService;
    private StockLeaveService stockLeaveService;

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap() {
        return employeeService.getAllMap();
    }
    
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_MANAGER', 'READ_USER')")
    @GetMapping("/dashboard")
    public Map<String, Object> getEmployee(){
        return employeeService.getEmployee();
    }
    
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Employee insert(@RequestBody UserRegistrationDto employee) {
        Employee e = employeeService.insert(employee);
        userEntityService.insert(employee);
        stockLeaveService.create(employee);
        userEntityService.sendVerifyMail(employee);
        return e;
    }

    @PutMapping("/add-project")
    public Object addProject(@RequestBody EmployeeProjectDto employeeProjectDto){
        return employeeService.addEmployeeToProject(employeeProjectDto);
    }

    @GetMapping("/stock-leave")
    public StockResponse getStock(){
        return employeeService.getUserStockLeave();
    }

    @GetMapping("/manager/list-staff")
    public List<Employee> findStaff(){
        return employeeService.findMyStaff();
    }
    
    @GetMapping("/manager/list-permission-staff")
    public List<Permission> findMyStaffPermission(){
        return employeeService.findMyStaffPermission();
    }
    
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Object getById(@PathVariable int id) {
        return employeeService.findById(id);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody UserRegistrationDto employee) {
        return employeeService.update(id, employee);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return employeeService.deleteById(id);
    }
}
