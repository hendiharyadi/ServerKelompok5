/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hendi
 */
@Repository
@Transactional
public interface OvertimeRepository extends JpaRepository<Overtime, Integer>{
    List<Overtime> findOvertimeByManager(Employee manager);
    
    @Query("SELECT o FROM Overtime as o WHERE o.employee.id= ?1 ORDER BY (CASE WHEN o.status = 'PENDING' THEN 0 WHEN o.status = 'ACCEPTED' THEN 1 ELSE 2 END)")
    List<Overtime> orderOvertime(Integer id);
}
