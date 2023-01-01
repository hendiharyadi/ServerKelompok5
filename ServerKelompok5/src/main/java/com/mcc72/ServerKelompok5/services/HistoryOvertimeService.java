/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.OvertimeDto;
import com.mcc72.ServerKelompok5.models.entity.HistoryOvertime;
import com.mcc72.ServerKelompok5.models.entity.Overtime;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.HistoryOvertimeRepository;
import java.sql.Timestamp;
import java.util.List;

import com.mcc72.ServerKelompok5.repositories.UserRepository;
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
public class HistoryOvertimeService {
    
    private HistoryOvertimeRepository historyOvertimeRepository;
    private UserRepository userRepository;
    
    public List<HistoryOvertime> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return user.getEmployee().getHistoryOvertimes();
    }
    
    public HistoryOvertime getById(int id){
        return historyOvertimeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public HistoryOvertime create(Overtime historyOvertime){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        HistoryOvertime ho = new HistoryOvertime();
        ho.setOvertime(historyOvertime);
        ho.setDate_history(new Timestamp(System.currentTimeMillis()));
        ho.setEmployee(user.getEmployee());
        return historyOvertimeRepository.save(ho);
    }
    
    public HistoryOvertime update(int id, OvertimeDto historyOvertime){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        HistoryOvertime ho = getById(id);
        ho.setEmployee(user.getEmployee());
        return ho;
    }
    
    public HistoryOvertime delete (int id){
        HistoryOvertime historyOvertime = getById(id);
        historyOvertimeRepository.delete(historyOvertime);
        return historyOvertime;
    }
}
