/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.models.dto;

import com.mcc72.ServerKelompok5.models.entity.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Hendi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRegistrationDto {

    private String first_name;

    private String last_name;

    private String email;

    private String phone_number;

    private String username;

    private String password;
    
    private List<Integer> roles; 
    
    private List<Integer> employees;

}
