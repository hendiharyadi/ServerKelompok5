package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.EmployeeProjectDto;
import com.mcc72.ServerKelompok5.models.dto.ProjectDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Project;
import com.mcc72.ServerKelompok5.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public class ProjectController {
    private ProjectService projectService;
    
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }
    
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/manager")
    public Object getAll(){
        return projectService.getAll();
    }
    
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public Project getById(@PathVariable int id) {
        return projectService.getById(id);
    }

    @GetMapping("/members/{id}")
    public List<Employee> getMembers(@PathVariable int id){
        return projectService.getMemberProject(id);
    }
    
    @PreAuthorize("hasAuthority('CREATE_MANAGER')")
    @PostMapping
    public Project create(@RequestBody ProjectDto projectDto){
        return projectService.create(projectDto);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Project update(@PathVariable int id, @RequestBody ProjectDto projectDto){
        return projectService.update(id, projectDto);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Project deleteProject(@PathVariable int id){
        return projectService.deleteProjectById(id);
    }
}
