/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.PermissionDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.models.entity.LeaveType;
import com.mcc72.ServerKelompok5.models.entity.Permission;
import com.mcc72.ServerKelompok5.models.entity.Status;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.PermissionRepository;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
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
    
    public List<Permission> getAll(){
        return permissionRepository.findAll();
    }
    
    public Permission getById(int id){
        return permissionRepository.findById(id).get();
    }
    
    public Permission create(PermissionDto permission){
        Permission permit = new Permission();
        LeaveType lt = permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN;
        permit.setLeave_type(lt);
        permit.setStart_leave(permission.getStart_leave());
        permit.setEnd_leave(permission.getEnd_leave());
        permit.setNote(permission.getNote());
        permit.setStatus(Status.PENDING);
        Employee e = employeeRepository.findById(permission.getEmployee()).get();
        permit.setEmployee(e);
        permit.setManager(e.getManager());
        return permissionRepository.save(permit);
    }
    
    public Permission update(Integer id, PermissionDto permission){
        Permission permit = new Permission();
        getById(id);
        permit.setId(id);
        LeaveType lt = permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN;
        permit.setLeave_type(lt);
        permit.setStart_leave(permission.getStart_leave());
        permit.setEnd_leave(permission.getEnd_leave());
        permit.setNote(permission.getNote());
        Status stat = permission.getStatus() ? Status.APPROVED : Status.REJECTED;
        permit.setStatus(stat);
        Employee e = employeeRepository.findById(permission.getEmployee()).get();
        permit.setEmployee(e);
        permit.setManager(e.getManager());
        return permissionRepository.save(permit);
    }
    
    public Permission delete (Integer id){
        Permission permission = getById(id);
        permissionRepository.delete(permission);
        return permission;
    }
    
    public void sendConfirmationMail(PermissionDto permission) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = employeeRepository.findById(permission.getEmployee()).get();
            Permission p = permissionRepository.findById(permission.getEmployee()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Confirmation email");
            String content = confirmationMailBuilder.build(e.getFirst_name(), p.getLeave_type(), p.getStatus());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
    
    public void sendRequestMail(PermissionDto permission) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = employeeRepository.findById(permission.getManager()).get();
            Permission p = permissionRepository.findById(permission.getEmployee()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Request email");
            String content = requestMailBuilder.build(e.getFirst_name(), p.getLeave_type());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
