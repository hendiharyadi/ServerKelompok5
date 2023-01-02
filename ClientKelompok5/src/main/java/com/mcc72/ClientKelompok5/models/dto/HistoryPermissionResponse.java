package com.mcc72.ClientKelompok5.models.dto;

import com.mcc72.ClientKelompok5.models.entities.Employee;
import com.mcc72.ClientKelompok5.models.entities.Overtime;
import com.mcc72.ClientKelompok5.models.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPermissionResponse {
    private int id;
    private Permission permission;
    private String date_history;
    private Employee employee;
}
