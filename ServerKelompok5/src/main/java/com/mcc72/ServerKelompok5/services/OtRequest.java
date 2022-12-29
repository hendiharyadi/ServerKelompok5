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
    
    public String build(String firstName, String note){
        Context context = new Context();
        context.setVariable("first_name", firstName);
        context.setVariable("note", note);
        return templateEngine.process("otrequestmail", context);
    }
}
