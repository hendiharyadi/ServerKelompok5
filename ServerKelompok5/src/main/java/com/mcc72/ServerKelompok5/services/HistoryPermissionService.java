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
import java.util.Date;
import java.util.List;
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
    
    public List<HistoryPermission> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return historyPermissionRepository.orderHistoryPermission(user.getEmployee().getId());
    }
    
    public HistoryPermission getById(int id){
        return historyPermissionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public HistoryPermission create(Permission historyPermission, int id){
       /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();*/
        HistoryPermission hp = new HistoryPermission();
        hp.setPermission(historyPermission);
//        hp.setDate_history(new Timestamp(System.currentTimeMillis()));
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        hp.setDate_history(ts);
        hp.setEmployee(userRepository.findById(id).get().getEmployee());
        return historyPermissionRepository.save(hp);
    }
    
    public HistoryPermission update(int id, HistoryPermission historyPermission){
        HistoryPermission hp = historyPermissionRepository.findById(id).get();
        getById(id);
        hp.setDate_history(historyPermission.getDate_history());
        return historyPermissionRepository.save(historyPermission);
    }
    
    public HistoryPermission delete (int id){
        HistoryPermission historyPermission = getById(id);
        historyPermissionRepository.delete(historyPermission);
        return historyPermission;
    }
}
