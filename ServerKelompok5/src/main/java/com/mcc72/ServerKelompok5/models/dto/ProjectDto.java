package com.mcc72.ServerKelompok5.models.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private String name;
    private String start_project;
    private String end_project;
    private List<Integer> employees;
}
