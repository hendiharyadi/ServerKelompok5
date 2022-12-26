/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.Overtime;
import com.mcc72.ServerKelompok5.repositories.OvertimeRepository;
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
public class OvertimeService {
    
    private OvertimeRepository overtimeRepository;
    
    public List<Overtime> getAll(){
        return overtimeRepository.findAll();
    }
    
    public Overtime getById(int id){
        return overtimeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public Overtime create(Overtime overtime){
        return overtimeRepository.save(overtime);
    }
    
    public Overtime update(int id, Overtime overtime){
        getById(id);
        overtime.setId(id);
        return overtimeRepository.save(overtime);
    }
    
    public Overtime delete (int id){
        Overtime overtime = getById(id);
        overtimeRepository.delete(overtime);
        return overtime;
    }
}
