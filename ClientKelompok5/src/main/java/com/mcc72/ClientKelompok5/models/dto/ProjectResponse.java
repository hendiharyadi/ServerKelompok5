package com.mcc72.ClientKelompok5.models.dto;

import lombok.Data;

@Data
public class ProjectResponse {
    private int id;

    private int members;
    private String name;
    private boolean status;
}
