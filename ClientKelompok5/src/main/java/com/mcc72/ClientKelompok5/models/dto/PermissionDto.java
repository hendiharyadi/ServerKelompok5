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
public class PermissionDto {
    
    private Boolean leave_type;
    private String start_leave;
    private String end_leave;
    private String note;
    private Boolean status;
    private int employee_id;
}
