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
    private Integer project_id;
    private Integer manager_id;
    private Integer employee_id;
    private Boolean status;
    private Timestamp date_history;
}
