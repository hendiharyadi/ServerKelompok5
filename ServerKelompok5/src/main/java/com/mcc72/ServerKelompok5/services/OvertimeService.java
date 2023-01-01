/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.OvertimeDto;
import com.mcc72.ServerKelompok5.models.entity.*;
import com.mcc72.ServerKelompok5.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class OvertimeService {
    
    private OvertimeRepository or;
    private EmployeeRepository er;
    private OtRequest otRequest;
    private OtConfirmation otConfirmation;
    private ProjectRepository pr;
    private JavaMailSender mailSender;
    private HistoryOvertimeService hos;

    private UserRepository userRepository;
    private final HistoryOvertimeRepository historyOvertimeRepository;

    public List<Overtime> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return user.getEmployee().getOvertimes();
    }

    public List<Overtime> findByManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return or.findAllByManager(user.getEmployee());
    }
    
    public Overtime getById(int id){
        return or.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public Overtime create(OvertimeDto o){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        Overtime overtime = new Overtime();
        overtime.setNote(o.getNote());
        overtime.setStart_overtime(o.getStart_overtime());
        overtime.setEnd_overtime(o.getEnd_overtime());
        overtime.setStatus(Status.PENDING);
        overtime.setEmployee(er.findById(user.getId()).get());
        Project p = pr.findById(o.getProject_id()).get();
        overtime.setProject(p);
        overtime.setManager(p.getManager());
        hos.create(overtime);
        return or.save(overtime);
    }
    
    public Overtime update(int id, OvertimeDto o){
        Overtime overtime = or.findById(id).get();
        Status stat = o.getStatus() ? Status.APPROVED : Status.REJECTED;
        overtime.setStatus(stat);
        return or.save(overtime);
    }
    
    public Overtime delete (int id){
        Overtime overtime = getById(id);
        or.delete(overtime);
        return overtime;
    }
    
    public void sendConfirmationMail(OvertimeDto overtime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = er.findById(user.getEmployee().getId()).get();
            Project p = pr.findById(overtime.getProject_id()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Confirmation email");
            String content = otConfirmation.build(e.getFirst_name(), overtime.getStatus()? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendRequestMail(OvertimeDto overtime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
        Employee e = er.findById(user.getEmployee().getId()).get();
        Project p = pr.findById(overtime.getProject_id()).get();
        messageHelper.setTo(p.getManager().getEmail());
        messageHelper.setSubject("Request email");
        String content = otRequest.build(overtime.getStart_overtime(),overtime.getEnd_overtime(), overtime.getNote(), e.getFirst_name());
        messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
