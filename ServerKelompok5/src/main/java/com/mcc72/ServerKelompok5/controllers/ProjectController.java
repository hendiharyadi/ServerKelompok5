package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.dto.ProjectDto;
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

    @GetMapping
    public List<Project> getAll(){
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable int id) {
        return projectService.getById(id);
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
    public Project delete(@PathVariable int id){
        return projectService.delete(id);
    }
}
