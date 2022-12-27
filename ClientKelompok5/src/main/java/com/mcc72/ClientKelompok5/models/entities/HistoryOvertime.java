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
public class HistoryOvertime {
    
    private Integer id;
    private Overtime overtime;
    private String date_history;
}
