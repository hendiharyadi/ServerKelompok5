package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ClientKelompok5.models.entities.Employee;
import com.mcc72.ClientKelompok5.services.EmployeeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeRestController {
    
    private EmployeeService employeeService;
    
    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getAll();
    }
    
    @GetMapping("/{id}")
    public Employee getById(@PathVariable int id){
        return employeeService.getById(id);
    }
    
    @PostMapping
    public Employee create (@RequestBody UserRegistrationDto employee){
        return employeeService.create(employee);
    }
    
    @PutMapping("/{id}")
    public Employee update (@PathVariable int id, @RequestBody UserRegistrationDto employee){
        return employeeService.update(id, employee);
    }
    
   @DeleteMapping("/{id}")
   public Employee delete (@PathVariable int id){
       return employeeService.delete(id);
   }
}
