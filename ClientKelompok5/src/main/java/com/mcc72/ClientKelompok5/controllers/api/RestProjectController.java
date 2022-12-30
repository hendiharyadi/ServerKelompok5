package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.ProjectDto;
import com.mcc72.ClientKelompok5.models.entities.Project;
import com.mcc72.ClientKelompok5.services.ProjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class RestProjectController {
    
    private ProjectService projectService;
    
    @GetMapping
    public List<Project> getAll(){
        return projectService.getAll();
    }
    
    @GetMapping("/{id}")
    public Project getById(@PathVariable int id){
        return projectService.getById(id);
    }

    //manager
    @PostMapping
    public Project create (@RequestBody ProjectDto project){
        return projectService.create(project);
    }
    
    @PutMapping("/{id}")
    public Project update (@PathVariable int id, @RequestBody ProjectDto project){
        return projectService.update(id, project);
    }
    
   @DeleteMapping("/{id}")
   public Project delete (@PathVariable int id){
       return projectService.delete(id);
   }
}
