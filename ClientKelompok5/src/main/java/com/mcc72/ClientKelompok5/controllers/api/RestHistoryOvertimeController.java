package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.HistoryOvertimeResponse;
import com.mcc72.ClientKelompok5.models.entities.HistoryOvertime;
import com.mcc72.ClientKelompok5.services.HistoryOvertimeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/history/overtime")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER')")
public class RestHistoryOvertimeController {
    
    private HistoryOvertimeService hoService;
    
    @GetMapping
    public List<HistoryOvertimeResponse> getAll(){
        return hoService.getAll();
    }
    
    @GetMapping("/{id}")
    public HistoryOvertimeResponse getById(@PathVariable int id){
        return hoService.getById(id);
    }
}
