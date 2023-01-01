package com.mcc72.ClientKelompok5.models.dto;

import lombok.Data;

@Data
public class EmployeeResponse {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
}
