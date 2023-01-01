package com.mcc72.ClientKelompok5.models.dto;

import com.mcc72.ClientKelompok5.models.entities.Employee;
import com.mcc72.ClientKelompok5.models.entities.Overtime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryOvertimeResponse {
    private int id;
    private Overtime overtime;
    private String date_history;
    private Employee employee;
}
