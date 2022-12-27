package com.mcc72.ClientKelompok5.models.entities;

import java.util.List;
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
public class Overtime {
    
    private Integer id;
    private String note;
    private String start_overtime;
    private String end_overtime;
    private Status status;
    private List<HistoryOvertime> historyOvertime;
    private Employee employee;
    private Employee manager;
    private Project project;
}
