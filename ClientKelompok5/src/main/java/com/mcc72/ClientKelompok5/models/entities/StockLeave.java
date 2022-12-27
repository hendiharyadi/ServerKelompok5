package com.mcc72.ClientKelompok5.models.entities;

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
public class StockLeave {
    
    private Integer id;
    private Employee employee;
    private Integer stock_available;
}
