/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.dto.ProjectDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Project;
import com.mcc72.ServerKelompok5.models.entity.UserEntity;
import com.mcc72.ServerKelompok5.repositories.EmployeeRepository;
import com.mcc72.ServerKelompok5.repositories.ProjectRepository;
import com.mcc72.ServerKelompok5.repositories.UserRepository;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hendi 
 */
@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    public Object getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return user.getEmployee().getProjects().stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("name", e.getName());
            map.put("start_project", e.getStart_project());
            map.put("end_project", e.getEnd_project());
            map.put("members", e.getEmployeeProject());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Employee> getMemberProject(int id) {
        Project project = projectRepository.findById(id).get();
        return project.getEmployeeProject();
    }

    public Project getById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    @Transactional
    public Project create(ProjectDto projectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        Project project = new Project();
        project.setManager(user.getEmployee());
        project.setName(projectDto.getName());
        project.setStart_project(projectDto.getStart_project());
        project.setEnd_project(projectDto.getEnd_project());
        List<Employee> employees = new ArrayList<>();
        for(Integer employee : projectDto.getEmployees()){
            employees.add(employeeRepository.findById(employee).get());
        }
        project.setEmployeeProject(employees);
        projectRepository.save(project);
        return project;
    }
    
    public List<Employee> selectByManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        return projectRepository.getEmployeeManager(user.getEmployee().getId());
    }
    
    public Project update(int id, ProjectDto projectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        Project project = projectRepository.findById(id).get();
        getById(id);
        project.setName(projectDto.getName());
        return projectRepository.save(project);
    }


    public Project deleteProjectById(int id) {
        Project project = projectRepository.findById(id).get();
        System.out.println("Project ID : " + id);
        projectRepository.deleteById(id);
        return project;
    }
}
