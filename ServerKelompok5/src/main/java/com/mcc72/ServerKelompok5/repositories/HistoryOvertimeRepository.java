/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.HistoryOvertime;
import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hendi
 */
@Repository
@Transactional
public interface HistoryOvertimeRepository extends JpaRepository<HistoryOvertime, Integer>{
    
    public Optional <HistoryOvertime> findById(Integer id);
    
    @Query("SELECT ho FROM HistoryOvertime as ho WHERE ho.employee.id= ?1 ORDER BY (CASE WHEN ho.overtime.status = 'PENDING' THEN 0 WHEN ho.overtime.status = 'ACCEPTED' THEN 1 ELSE 2 END)")
    List<HistoryOvertime> orderHistoryOvertime(Integer id);
}
