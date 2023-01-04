package com.mcc72.ClientKelompok5.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author HP
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    
    private String name;
    private String start_project;
    private String end_project;

    private List<Integer> employees;


}
