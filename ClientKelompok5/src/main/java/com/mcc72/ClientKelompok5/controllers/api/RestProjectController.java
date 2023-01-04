package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.ProjectDto;
import com.mcc72.ClientKelompok5.models.dto.ProjectResponse;
import com.mcc72.ClientKelompok5.models.entities.Employee;
import com.mcc72.ClientKelompok5.models.entities.Project;
import com.mcc72.ClientKelompok5.services.ProjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public class RestProjectController {
    
    private ProjectService projectService;
    
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<ProjectResponse> getAll(){
        return projectService.getAll();
    }

    @GetMapping("/members/{id}")
    public List<Employee> getAllMembers(@PathVariable int id){
        return projectService.getAllMembers(id);
    }
    
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public ProjectResponse getById(@PathVariable int id){
        return projectService.getById(id);
    }

    @PreAuthorize("hasAuthority('CREATE_MANAGER')")
    @PostMapping
    public Project create (@RequestBody ProjectDto project){
        return projectService.create(project);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Project update (@PathVariable int id, @RequestBody ProjectDto project){
        return projectService.update(id, project);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Project delete (@PathVariable int id){
       return projectService.delete(id);
   }
}
