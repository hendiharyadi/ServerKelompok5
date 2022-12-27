package com.mcc72.ServerKelompok5.models.dto;

import java.sql.Timestamp;
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
public class OvertimeDto {
    
    private String start_overtime;
    private String end_overtime;
    private String note;
    private int employee_id;
    private int project_id;
    private int manager_id;
    private Boolean status;
    private Timestamp date_history;
}
