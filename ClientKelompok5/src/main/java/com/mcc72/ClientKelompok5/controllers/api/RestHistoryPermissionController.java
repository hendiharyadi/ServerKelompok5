package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.entities.HistoryPermission;
import com.mcc72.ClientKelompok5.services.HistoryPermissionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/history-permission")
@AllArgsConstructor
public class RestHistoryPermissionController {
    
    private HistoryPermissionService hpService;
    
    @GetMapping
    public List<HistoryPermission> getAll(){
        return hpService.getAll();
    }
    
    @GetMapping("/{id}")
    public HistoryPermission getById(@PathVariable int id){
        return hpService.getById(id);
    }
}
