package com.mcc72.ClientKelompok5.models.entities;

import java.util.List;
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
public class UserEntity {
    
    private Integer id;
    private String username;
    private String password;
    private Boolean isActive;
    private String verificationCode;
    private Integer failedAttempt;
    private Employee employee;
    private List<Role> userRole;
}
