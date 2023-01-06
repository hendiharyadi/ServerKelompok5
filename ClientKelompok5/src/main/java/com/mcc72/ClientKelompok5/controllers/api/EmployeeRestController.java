package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.EmployeeResponse;
import com.mcc72.ClientKelompok5.models.dto.ManagersResponse;
import com.mcc72.ClientKelompok5.models.dto.StockResponse;
import com.mcc72.ClientKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ClientKelompok5.models.entities.Employee;
import com.mcc72.ClientKelompok5.models.entities.StockLeave;
import com.mcc72.ClientKelompok5.models.entities.UserEntity;
import com.mcc72.ClientKelompok5.services.EmployeeService;
import java.util.List;

import com.mcc72.ClientKelompok5.services.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
public class EmployeeRestController {
    private ManagerService managerService;
    private EmployeeService employeeService;
    
    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getAll();
    }

    @GetMapping("/dashboard")
    public Employee getUserLoginData(){
        return employeeService.employeeLogin();
    }
    
//    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable int id){
        return employeeService.getById(id);
    }

    @GetMapping("/managers")
    public List<Employee> getAllManagers(){
        return managerService.getManagers();
    }

    @GetMapping("/stock-leave")
    public StockResponse getStockLeave(){
        return employeeService.getStock();
    }

    @GetMapping("/get-stock")
    public String test(){
        return "Success";
    }

    @GetMapping("/manager/list-staff")
    public List<EmployeeResponse> getStaff(){
        return employeeService.getMyStaff();
    }
    
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Employee create (@RequestBody UserRegistrationDto employee){
        return employeeService.create(employee);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Employee update (@PathVariable int id, @RequestBody UserRegistrationDto employee){
        return employeeService.update(id, employee);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete (@PathVariable int id){
       return employeeService.delete(id);
   }
}
