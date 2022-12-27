package com.mcc72.ClientKelompok5.models.entities;

import java.security.Permission;
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
public class HistoryPermission {
    
    private Integer id;
    private Permission permission;
    private String date_history;
}
