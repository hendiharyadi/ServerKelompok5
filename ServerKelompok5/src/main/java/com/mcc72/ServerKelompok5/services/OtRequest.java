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
public class OtRequest {
    
    private TemplateEngine templateEngine;
    
    public String build(String start, String end, String note, String firstName){
        Context context = new Context();
        context.setVariable("start_overtime", start);
        context.setVariable("end_overtime", end);
        context.setVariable("note", note);
        context.setVariable("first_name", firstName);
        return templateEngine.process("otrequestmail", context);
    }
}
