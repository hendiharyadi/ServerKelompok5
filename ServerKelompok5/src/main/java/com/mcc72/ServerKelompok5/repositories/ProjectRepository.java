/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.repositories;

import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Project;
import java.util.List;
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
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    
    @Query("select e from Employee e where e.manager.id = ?1")
    List<Employee> getEmployeeManager(Integer id);
    
}
