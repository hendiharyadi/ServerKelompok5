/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.StockLeave;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Hendi
 */
public interface StockLeaveRepository extends JpaRepository<StockLeave, Integer>{
 
    
}
