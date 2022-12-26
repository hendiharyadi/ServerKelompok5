/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.OvertimeDto;
import com.mcc72.ServerKelompok5.models.entity.Overtime;
import com.mcc72.ServerKelompok5.models.entity.Status;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.OvertimeRepository;
import com.mcc72.ServerKelompok5.repositories.ProjectRepository;
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
    
    private OvertimeRepository or;
    private EmployeeRepository er;
    private ProjectRepository pr;
    
    public List<Overtime> getAll(){
        return or.findAll();
    }
    
    public Overtime getById(int id){
        return or.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public Overtime create(OvertimeDto o){
        Overtime overtime = new Overtime();
        overtime.setNote(o.getNote());
        overtime.setStart_overtime(o.getStart_overtime());
        overtime.setEnd_overtime(o.getEnd_overtime());
        overtime.setStatus(Status.PENDING);
        overtime.setEmployee(er.findById(o.getEmployee_id()).get());
//        overtime.setManager(er.findById(o.getManager_id()).get());
        overtime.setProject(pr.findById(o.getProject_id()).get());
        return or.save(overtime);
    }
    
    public Overtime update(int id, OvertimeDto o){
    
        if(!or.existsById(id)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not exist.");
        }
        
        Overtime overtime = or.findById(id).get();
        overtime.setNote(o.getNote());
        overtime.setStart_overtime(o.getStart_overtime());
        overtime.setEnd_overtime(o.getEnd_overtime());
//        if(o.isStatus()){
//            overtime.setStatus(Status.APPROVED);
//        }
//        else {
//            overtime.setStatus(Status.REJECTED);
//        }
        Status stat = o.isStatus() ? Status.APPROVED : Status.REJECTED;
        overtime.setStatus(stat);
        overtime.setEmployee(er.findById(o.getEmployee_id()).get());
//        overtime.setManager(er.findById(o.getManager_id()).get());
        overtime.setProject(pr.findById(o.getProject_id()).get());
        return or.save(overtime);
    }
    
    public Overtime delete (int id){
        Overtime overtime = getById(id);
        or.delete(overtime);
        return overtime;
    }
}
