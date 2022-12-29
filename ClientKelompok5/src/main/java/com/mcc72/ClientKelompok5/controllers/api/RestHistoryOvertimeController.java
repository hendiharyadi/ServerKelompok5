package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.entities.HistoryOvertime;
import com.mcc72.ClientKelompok5.services.HistoryOvertimeService;
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
@RequestMapping("/api/history-overtime")
@AllArgsConstructor
public class RestHistoryOvertimeController {
    
    private HistoryOvertimeService hoService;
    
    @GetMapping
    public List<HistoryOvertime> getAll(){
        return hoService.getAll();
    }
    
    @GetMapping("/{id}")
    public HistoryOvertime getById(@PathVariable int id){
        return hoService.getById(id);
    }
}
