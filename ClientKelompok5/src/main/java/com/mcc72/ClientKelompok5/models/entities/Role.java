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
public class Role {
    
    private Integer id;
    private String name;
    private List<UserEntity> userRole;
    private List<Privilege> privileges;
}
