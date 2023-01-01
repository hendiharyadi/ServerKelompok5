package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.services.HistoryPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
public class HistoryPermissionController {
    private HistoryPermissionService historyPermissionService;

    @GetMapping("/permission")
    public List<HistoryPermission> getHistoryPermission(){
        return historyPermissionService.getAll();
    }
}
