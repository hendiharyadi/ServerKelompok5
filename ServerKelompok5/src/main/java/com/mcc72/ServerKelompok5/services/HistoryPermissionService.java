/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.repositories.HistoryPermissionRepository;
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
    
    public List<HistoryPermission> getAll(){
        return historyPermissionRepository.findAll();
    }
    
    public HistoryPermission getById(int id){
        return historyPermissionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public HistoryPermission create(HistoryPermission historyPermission){
        return historyPermissionRepository.save(historyPermission);
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
