/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.Employee;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hendi
 */
@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    
    public Optional <Employee> findByEmail(String email);
    
    @Modifying
    @Query("update StockLeave sl set sl.stock_available= ?1 where sl.id = ?2")
    int setStockLeave(Integer stock_available, Integer id);

}
