/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;


import com.mcc72.ServerKelompok5.models.entity.Role;
import com.mcc72.ServerKelompok5.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class RoleService {
    
    private RoleRepository rr;
   public List<Role> findAll() {
        if (rr.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data available");
        }
        
        for (Role role : rr.findAll()) {
            role.getName();
        }
        
        return rr.findAll();
    }
    
     public List<Map<String, Object>> getAllMap() {

        return rr.findAll().stream().map(role -> {
            Map<String, Object> m = new HashMap<>();
            m.put("roleId", role.getId());
            m.put("roleName", role.getName());
//            m.put("regionName", role.getRegion().getName());
            return m;
        }).collect(Collectors.toList());
//        return list;
    }


    public Role findById(Integer id) {
        if (!rr.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist");
        }
        return rr.findById(id).get();
    }

    // DTO without model mapper
    public Role insert(Role r) {
                if (rr.existsById(r.getId())) {
                    throw new ResponseStatusException(HttpStatus.FOUND, "Data is exist!!!");
                }
            
                return rr.save(r);
            }
        

    public Role update(Role r) {
        if(!rr.existsById(r.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist");
        }
        
        return rr.save(r);
    }

    public String deleteById(Integer id) {
        if (!rr.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist");
        }
        Role r = findById(id);
        rr.deleteById(id);
        return "Delete for " + r.getName() + "success!!!";
    }

}
