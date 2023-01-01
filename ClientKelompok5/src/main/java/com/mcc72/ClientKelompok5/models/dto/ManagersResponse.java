package com.mcc72.ClientKelompok5.models.dto;

import com.mcc72.ClientKelompok5.models.entities.Employee;
import lombok.Data;

import java.util.List;

@Data
public class ManagersResponse {
    private List<Employee> managers;
}
