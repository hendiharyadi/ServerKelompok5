package com.mcc72.ClientKelompok5.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author HP
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
    private Integer role_id;
    private Integer manager_id;
}
