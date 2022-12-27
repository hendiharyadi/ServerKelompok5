package com.mcc72.ClientKelompok5.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author HP
 */
@Data
@NoArgsConstructor 
@AllArgsConstructor
public class OvertimeDto {
    
    private String start_overtime;
    private String end_overtime;
    private String note;
    private int employee_id;
    private int project_id;
    private boolean status;
}
