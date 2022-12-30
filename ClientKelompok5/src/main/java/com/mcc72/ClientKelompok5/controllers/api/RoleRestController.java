package com.mcc72.ClientKelompok5.controllers.api;

import com.mcc72.ClientKelompok5.models.dto.RoleResponse;
import com.mcc72.ClientKelompok5.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleRestController {
    private RoleService roleService;

    @GetMapping
    public List<RoleResponse> all(){
        return roleService.getRoles();
    }
}
