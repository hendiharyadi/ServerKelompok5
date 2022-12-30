/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.PermissionDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.LeaveType;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.models.entity.Status;
import com.mcc72.ServerKelompok5.models.entity.StockLeave;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.PermissionRepository;
import com.mcc72.ServerKelompok5.repositories.StockLeaveRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hendi
 */

@Service
@AllArgsConstructor
public class PermissionService {
    
    private PermissionRepository permissionRepository;
    private EmployeeRepository employeeRepository;
    private ConfirmationMailBuilder confirmationMailBuilder;
    private RequestMailBuilder requestMailBuilder;
    private JavaMailSender mailSender;
    private HistoryPermissionService hps;
    private StockLeaveRepository slr;
    private UserRepository userRepository;
    
    public List<Permission> getAll(){
        return permissionRepository.findAll();
    }
    
    public Permission getById(int id){
        return permissionRepository.findById(id).get();
    }
    
    public Permission create(PermissionDto permission){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        StockLeave sl = slr.findById(permission.getEmployee()).get();
        Permission permit = new Permission();
        LeaveType lt = permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN;
        Employee e = employeeRepository.findById(permission.getEmployee()).get();
        if(sl.getStock_available() <= 0 && permission.getLeave_type().equals(true)){
            throw new Error("Your cuti quota has been running out. Please wait until next year.");
        } else {
            permit.setLeave_type(lt);
            permit.setStart_leave(permission.getStart_leave());
            permit.setEnd_leave(permission.getEnd_leave());
            permit.setNote(permission.getNote());
            permit.setStatus(Status.PENDING);
            permit.setEmployee(employeeRepository.findById(user.getId()).get());
            permit.setManager(e.getManager());
//                if(permit.getStart_leave().equals(permission.getStart_leave())){
//                    throw new Error("You can't pick this date. Please choose another date.");
//                } else{
                    hps.create(permit);
                    return permissionRepository.save(permit); 
//                }
        }
    }
    
    public Permission update(Integer id, PermissionDto permission){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        Permission permit = permissionRepository.findById(id).get();
        Status stat = permission.getStatus() ? Status.APPROVED : Status.REJECTED;
        permit.setStatus(stat);
        permit.setEmployee(employeeRepository.findById(user.getId()).get());
        return permissionRepository.save(permit);
    }
    
    public Permission delete (Integer id){
        Permission permission = getById(id);
        permissionRepository.delete(permission);
        return permission;
    }
    
    public void sendConfirmationMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = employeeRepository.findById(user.getId()).get();
            Permission p = permissionRepository.findById(user.getId()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Confirmation email");
            String content = confirmationMailBuilder.build(e.getFirst_name(), p.getLeave_type(), p.getStatus());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
    
    public void sendRequestMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = employeeRepository.findById(permission.getManager()).get();
            Permission p = permissionRepository.findById(user.getId()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Request email");
            String content = requestMailBuilder.build(e.getFirst_name(), p.getLeave_type());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
