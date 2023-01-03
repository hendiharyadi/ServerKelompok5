/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.PermissionDto;
import com.mcc72.ServerKelompok5.models.dto.UserRegistrationDto;
import com.mcc72.ServerKelompok5.models.entity.StockLeave;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.StockLeaveRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
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
public class StockLeaveService {
    
    private StockLeaveRepository stockLeaveRepository;
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;
    
    public List<StockLeave> getAll(){
        return stockLeaveRepository.findAll();
    }
    
    public StockLeave getById(int id){
        return stockLeaveRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public StockLeave create(UserRegistrationDto stockLeave){
        StockLeave sl = new StockLeave();
        sl.setStock_available(12);
        sl.setEmployee(employeeRepository.findByEmail(stockLeave.getEmail()).get());
        return stockLeaveRepository.save(sl);
    }
    
    public StockLeave update(int id, StockLeave stockLeave){
        getById(id);
        stockLeave.setId(id);
        return stockLeaveRepository.save(stockLeave);
    }
    
    public StockLeave delete (int id){
        StockLeave stockLeave = getById(id);
        stockLeaveRepository.delete(stockLeave);
        return stockLeave;
    }
    
    public void updateCuti(int id, Integer permission) {
        System.out.println("updateAttempt here");
        UserEntity user = userRepository.findById(id).get();
        employeeRepository.setStockLeave(user.getEmployee().getStockLeave().getStock_available() - permission, user.getEmployee().getId());
        System.out.println("updateAttempt success");
    }
}
