package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.OvertimeDto;
import com.mcc72.ClientKelompok5.models.entities.Overtime;
import com.mcc72.ClientKelompok5.services.OvertimeService;
import java.util.List;
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
@RequestMapping("/api/overtime")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class RestOvertimeController {
    
    private OvertimeService overtimeService;
    
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public List<Overtime> getAll(){
        return overtimeService.getAll();
    }

    @GetMapping("/manager")
    public List<Overtime> getAllByManager(){
        return overtimeService.getAllByManager();
    }

    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public Overtime getById(@PathVariable int id){
        return overtimeService.getById(id);
    }
    
    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_MANAGER')")
    @PostMapping
    public Overtime create (@RequestBody OvertimeDto overtime){
        return overtimeService.create(overtime);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Overtime update (@PathVariable int id, @RequestBody OvertimeDto overtime){
        return overtimeService.update(id, overtime);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Overtime delete (@PathVariable int id){
       return overtimeService.delete(id);
   }
}
