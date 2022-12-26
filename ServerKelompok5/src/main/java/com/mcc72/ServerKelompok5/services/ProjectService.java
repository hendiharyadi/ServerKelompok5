/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.services;

import com.mcc72.ServerKelompok5.models.entity.Project;
import com.mcc72.ServerKelompok5.repositories.ProjectRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Hendi
 */
@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    
    public List<Project> getAll(){
        return projectRepository.findAll();
    }
    
    public Project getById(int id){
        return projectRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }
    
    public Project create(Project project){
        return projectRepository.save(project);
    }
    
    public Project update(int id, Project project){
        getById(id);
        project.setId(id);
        return projectRepository.save(project);
    }
    
    public Project delete (int id){
        Project project = getById(id);
        projectRepository.delete(project);
        return project;
    }
}
