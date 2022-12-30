/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.LeaveType;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class RequestMailBuilder {
    
    private TemplateEngine templateEngine;
    
    public String build(LeaveType leaveType, String startLeave, String endLeave, String employee){
        Context context = new Context();
        context.setVariable("leave_type", leaveType);
        context.setVariable("start_leave", startLeave);
        context.setVariable("end_leave", endLeave);
        context.setVariable("first_name", employee);
        return templateEngine.process("requestmail", context);
    }
}
