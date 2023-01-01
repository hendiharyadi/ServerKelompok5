package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.entity.HistoryOvertime;
import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.services.HistoryOvertimeService;
import com.mcc72.ServerKelompok5.services.HistoryPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/history-overtime")
public class HistoryOvertimeController {
    private HistoryOvertimeService historyOvertimeService;
    @GetMapping()
    public List<HistoryOvertime> getHistoryOvertimes(){
        return historyOvertimeService.getAll();
    }

    @GetMapping("/{id}")
    public HistoryOvertime getDetail(@PathVariable int id){
        return historyOvertimeService.getById(id);
    }
}
