/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.HistoryPermissionRepository;
import com.mcc72.ServerKelompok5.repositories.PermissionRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class HistoryPermissionService {
    
    private HistoryPermissionRepository historyPermissionRepository;
    private PermissionRepository permissionRepository;
    private UserRepository userRepository;
    
    public Object getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return user.getEmployee().getPermissions().stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("history permission", e.getHistories());
            return map;
        }).collect(Collectors.toList());
    }
    
    public HistoryPermission getById(int id){
        return historyPermissionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public HistoryPermission create(Permission historyPermission){
        HistoryPermission hp = new HistoryPermission();
        hp.setPermission(historyPermission);
        hp.setDate_history(new Timestamp(System.currentTimeMillis()));
        return historyPermissionRepository.save(hp);
    }
    
    public HistoryPermission update(int id, HistoryPermission historyPermission){
        HistoryPermission hp = new HistoryPermission();
        getById(id);
        hp.setId(id);
        hp.setDate_history(historyPermission.getDate_history());
        return historyPermissionRepository.save(historyPermission);
    }
    
    public HistoryPermission delete (int id){
        HistoryPermission historyPermission = getById(id);
        historyPermissionRepository.delete(historyPermission);
        return historyPermission;
    }
}
