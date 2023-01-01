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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
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
            map.put("status", e.getStatus());
//            map.put("members", e.getEmployeeProject().size());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Employee> getMemberProject(int id) {
        Project project = projectRepository.findById(id).get();
        return null;
    }

    public Project getById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    @Transactional
    public Project create(ProjectDto projectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();

        Employee emp1 = employeeRepository.findById(17).get();
        Employee emp2 = employeeRepository.findById(18).get();
        Employee emp3 = employeeRepository.findById(19).get();
        List<Employee> employees = Arrays.asList(emp1, emp2, emp3);

        Project project = new Project();
        project.setStatus(false);
        project.setManager(user.getEmployee());
        project.setName(projectDto.getName());
        project.setEmployeeProject(employees);

        projectRepository.save(project);
        return project;
    }

    public Project update(int id, ProjectDto projectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName()).get();
        Project project = projectRepository.findById(id).get();
        getById(id);
        project.setStatus(projectDto.getStatus());
        project.setName(projectDto.getName());
        return projectRepository.save(project);
    }


    public Project delete(int id) {
        Project project = getById(id);
        projectRepository.delete(project);
        return project;
    }
}
