package com.mcc72.ServerKelompok5.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private String name;
    private Integer managerId;
    private Boolean status;
}
