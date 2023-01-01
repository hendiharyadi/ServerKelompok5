package com.mcc72.ClientKelompok5.models.dto;

import lombok.Data;

@Data
public class PermissionResponse {
    private int id;
    private String leave_type;
    private String start_leave;
    private String end_leave;
    private String note;
    private String status;
}
