package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.entity.Privilege;
import com.mcc72.ServerKelompok5.services.PrivilegeService;
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
import org.springframework.security.access.prepost.PreAuthorize;

@AllArgsConstructor
@RestController
@RequestMapping("privilege")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PrivilegeController {
    
    private PrivilegeService privilegeService;
    
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return privilegeService.getAllMap();
    }
    
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Privilege insert(@RequestBody Privilege privilege){
        return privilegeService.insert(privilege);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping
    public Privilege update(@RequestBody Privilege privilege){
        return privilegeService.update(privilege);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        return privilegeService.deleteById(id);
    }
}
