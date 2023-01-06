package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.OvertimeDto;
import com.mcc72.ServerKelompok5.models.entity.Overtime;
import com.mcc72.ServerKelompok5.services.HistoryOvertimeService;
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
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class OvertimeController {
    
    private OvertimeService overtimeService;
    private HistoryOvertimeService historyOvertimeService;
    
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public Object getAll() {
        return overtimeService.getAll();
    }
    
    @GetMapping("/manager")
    public List<Overtime> findByManager(){
        return overtimeService.findByManager();
    }
    
    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_MANAGER')")
    @PostMapping
    public Overtime create(@RequestBody OvertimeDto overtime){
        Overtime ot = overtimeService.create(overtime);
        overtimeService.sendRequestMail(overtime);
        return ot;
    }
    
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Overtime update(@PathVariable Integer id, @RequestBody OvertimeDto overtime){
        Overtime ot = overtimeService.update(id, overtime);
        if (overtime.getStatus().equals(true)){
            overtimeService.sendConfirmationMail(id, overtime);
        } else {
            overtimeService.sendConfirmationMail(id, overtime);
        }
        return ot;
    }
    
    @PreAuthorize("hasAnyAuthority('READ_MANAGER', 'READ_ADMIN')")
    @GetMapping("/{id}")
    public Overtime getById(@PathVariable int id){
        return overtimeService.getById(id);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Overtime delete(@PathVariable int id){
        return overtimeService.delete(id);
    }
}
