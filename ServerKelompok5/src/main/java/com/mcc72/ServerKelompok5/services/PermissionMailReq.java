/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.LeaveType;
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
public class PermissionMailReq {
    
    private TemplateEngine templateEngine;
    
    public String build (LeaveType leaveType, String start, String end, String note, String firstName){
        Context context = new Context();
        context.setVariable("leave_type", leaveType);
        context.setVariable("start_leave", start);
        context.setVariable("end_leave", end);
        context.setVariable("note", note);
        context.setVariable("first_name", firstName);
        return templateEngine.process("permissioneqmail", context);
    }
}
