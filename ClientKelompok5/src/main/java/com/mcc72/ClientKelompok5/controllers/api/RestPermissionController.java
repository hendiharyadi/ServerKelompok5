package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.HistoryPermissionResponse;
import com.mcc72.ClientKelompok5.models.dto.PermissionDto;
import com.mcc72.ClientKelompok5.models.dto.PermissionResponse;
import com.mcc72.ClientKelompok5.models.entities.Permission;
import com.mcc72.ClientKelompok5.services.HistoryPermissionService;
import com.mcc72.ClientKelompok5.services.PermissionService;
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
@RequestMapping("/api/permission")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class RestPermissionController {
    
    private PermissionService permissionService;
    private HistoryPermissionService historyPermissionService;
    
    @GetMapping
    public List<PermissionResponse> getAll(){
        return permissionService.getAll();
    }

    @GetMapping("/manager")
    public List<Permission> getAllByManager(){
        return permissionService.getAllByManager();
    }
    
    @GetMapping("/{id}")
    public PermissionResponse getById(@PathVariable int id){
        return permissionService.getById(id);
    }
    
    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_MANAGER')")
    @PostMapping
    public PermissionResponse create (@RequestBody PermissionDto permission){
        return permissionService.create(permission);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public PermissionResponse update (@PathVariable int id, @RequestBody PermissionDto permission){
        return permissionService.update(id, permission);
    }
    
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Permission delete (@PathVariable int id){
       return permissionService.delete(id);
   }

   @GetMapping("/history")
    public List<HistoryPermissionResponse> getHistory(){
        return historyPermissionService.getAll();
   }
}
