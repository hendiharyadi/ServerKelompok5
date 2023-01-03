package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.EmployeeProjectDto;
import com.mcc72.ServerKelompok5.models.dto.ProjectDto;
import com.mcc72.ServerKelompok5.models.entity.Employee;
import com.mcc72.ServerKelompok5.models.entity.Project;
import com.mcc72.ServerKelompok5.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;

    @GetMapping("/manager")
    public Object getAll(){
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable int id) {
        return projectService.getById(id);
    }

    @GetMapping("/members/{id}")
    public List<Employee> getMembers(@PathVariable int id){
        return projectService.getMemberProject(id);
    }



    @PostMapping
    public Project create(@RequestBody ProjectDto projectDto){
        return projectService.create(projectDto);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable int id, @RequestBody ProjectDto projectDto){
        return projectService.update(id, projectDto);
    }

    @DeleteMapping("/{id}")
    public Project deleteProject(@PathVariable int id){
        return projectService.deleteProjectById(id);
    }
}
