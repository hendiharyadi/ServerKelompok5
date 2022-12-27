/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.PermissionDto;
import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.repositories.HistoryPermissionRepository;
import com.mcc72.ServerKelompok5.repositories.PermissionRepository;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    
    public List<HistoryPermission> getAll(){
        return historyPermissionRepository.findAll();
    }
    
    public HistoryPermission getById(int id){
        return historyPermissionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public HistoryPermission create(PermissionDto historyPermission){
        HistoryPermission hp = new HistoryPermission();
        hp.setDate_history(historyPermission.getDate_history());
        return historyPermissionRepository.save(hp);
    }
    
    public HistoryPermission update(int id, HistoryPermission historyPermission){
        getById(id);
        historyPermission.setId(id);
        return historyPermissionRepository.save(historyPermission);
    }
    
    public HistoryPermission delete (int id){
        HistoryPermission historyPermission = getById(id);
        historyPermissionRepository.delete(historyPermission);
        return historyPermission;
    }
}
