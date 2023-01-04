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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private PermissionMailReq pmr;
    private PermissionMailConf pmc;
    

    public List<Permission> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return permissionRepository.orderPermission(user.getEmployee().getId());
//        return permissionRepository.findAll();
    }

    public List<Permission> getByManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return permissionRepository.findPermissionByManager(user.getEmployee());
    }
    
    public Permission getById(int id){
        return permissionRepository.findById(id).get();
    }
    
    public Permission create(PermissionDto permission){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        StockLeave sl = user.getEmployee().getStockLeave();
        LeaveType lt = permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN;
        Employee e = user.getEmployee();
        if(sl.getStock_available() <= 0 && permission.getLeave_type().equals(true)){
            throw new Error("Your cuti quota has been running out. Please wait until next year.");
        } else {
            Permission permit = new Permission();
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
                    hps.create(permit, e.getId());
                    return permissionRepository.save(permit); 
//                }
        }
    }
    
    public Permission update(Integer id, PermissionDto permission){
        Permission permit = permissionRepository.findById(id).get();
        Status stat = permission.getStatus() ? Status.APPROVED : Status.REJECTED;
        permit.setStatus(stat);
        hps.create(permit, permit.getEmployee().getId());
        return permissionRepository.save(permit);
    }
    
    public Permission delete (Integer id){
        Permission permission = getById(id);
        permissionRepository.delete(permission);
        return permission;
    }
    
    public void sendConfirmationMail(Integer id, PermissionDto permission) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Permission permit = permissionRepository.findById(id).get();
            messageHelper.setTo(permit.getEmployee().getEmail());
            messageHelper.setSubject("Leave Confirmation Email");
            String content = confirmationMailBuilder.build(permit.getEmployee().getFirst_name(), permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStatus() ? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
    
    public void sendRequestMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee m = employeeRepository.findById(user.getEmployee().getManager().getId()).get();
            Employee e = employeeRepository.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(m.getEmail());
            messageHelper.setSubject("Cuti Request Email");
            String content = requestMailBuilder.build(permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStart_leave(), permission.getEnd_leave(), e.getFirst_name());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
    
    public void sendConfirmationPermitMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = user.getEmployee();
            Permission p = permissionRepository.findById(user.getEmployee().getId()).get(); 
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Izin Confirmation email");
            String content = pmc.build(e.getFirst_name(), permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStatus() ? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
    
    public void sendRequestPermitMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee m = employeeRepository.findById(user.getEmployee().getManager().getId()).get();
            Employee e = employeeRepository.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(m.getEmail());
            messageHelper.setSubject("Izin Request email");
            String content = pmr.build(permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStart_leave(), permission.getEnd_leave(), permission.getNote(), e.getFirst_name());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
