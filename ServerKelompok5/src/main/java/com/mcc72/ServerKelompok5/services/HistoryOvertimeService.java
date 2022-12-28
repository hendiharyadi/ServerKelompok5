/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.OvertimeDto;
import com.mcc72.ServerKelompok5.models.entity.HistoryOvertime;
import com.mcc72.ServerKelompok5.models.entity.Overtime;
import com.mcc72.ServerKelompok5.repositories.HistoryOvertimeRepository;
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
public class HistoryOvertimeService {
    
    private HistoryOvertimeRepository historyOvertimeRepository;
    
    public List<HistoryOvertime> getAll(){
        return historyOvertimeRepository.findAll();
    }
    
    public HistoryOvertime getById(int id){
        return historyOvertimeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public HistoryOvertime create(Overtime historyOvertime){
        HistoryOvertime ho = new HistoryOvertime();
        ho.setOvertime(historyOvertime);
        ho.setDate_history(new Timestamp(System.currentTimeMillis()));
        return historyOvertimeRepository.save(ho);
    }
    
    public HistoryOvertime update(int id, OvertimeDto historyOvertime){
        HistoryOvertime ho = new HistoryOvertime();
        getById(id);
        ho.setId(id);
        ho.setDate_history(historyOvertime.getDate_history());
        return ho;
    }
    
    public HistoryOvertime delete (int id){
        HistoryOvertime historyOvertime = getById(id);
        historyOvertimeRepository.delete(historyOvertime);
        return historyOvertime;
    }
}
