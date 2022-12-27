package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.OvertimeDto;
import com.mcc72.ServerKelompok5.models.entity.Overtime;
import com.mcc72.ServerKelompok5.services.OvertimeService;
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
@AllArgsConstructor
@RestController
@RequestMapping("overtime")
//@PreAuthorize("hasRole('ROLE_USER')")
public class OvertimeController {
    
    private OvertimeService overtimeService;
    
//    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public List<Overtime> getAll() {
        return overtimeService.getAll();
    }
    
    @PostMapping
    public Overtime create(@RequestBody OvertimeDto overtime){
        return overtimeService.create(overtime);
    }
    
    @PutMapping("/{id}")
    public Overtime update(@PathVariable int id, @RequestBody OvertimeDto overtime){
        return overtimeService.update(id, overtime);
    }
    
    @GetMapping("/{id}")
    public Overtime getById(@PathVariable int id){
        return overtimeService.getById(id);
    }
    
    @DeleteMapping("/{id}")
    public Overtime delete(@PathVariable int id){
        return overtimeService.delete(id);
    }
}
