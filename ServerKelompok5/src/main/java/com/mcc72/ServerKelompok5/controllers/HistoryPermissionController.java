
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcc72.ServerKelompok5.controllers;

import com.mcc72.ServerKelompok5.models.entity.HistoryPermission;
import com.mcc72.ServerKelompok5.services.HistoryPermissionService;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@AllArgsConstructor
@RestController
@RequestMapping("/history-permission")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER')")
public class HistoryPermissionController {
    private HistoryPermissionService historyPermissionService;

    @GetMapping()
    public List<HistoryPermission> getHistoryPermission() {
        return historyPermissionService.getAll();
    }

    @GetMapping("/{id}")
    public HistoryPermission getDetail(@PathVariable int id){
       return historyPermissionService.getById(id);
    }
}
