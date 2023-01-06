package com.mcc72.ClientKelompok5.controllers;

import com.mcc72.ClientKelompok5.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class MainController {
    private EmployeeService employeeService;

    @RequestMapping
    public String redirectToDashboard(){
        return "redirect:/dashboard";
    }

    @RequestMapping("/try")
    public String tryLogin(){
        return "auth/login";
    }

    @RequestMapping("/dashboard")
    public String getEmployee(Model model){
        model.addAttribute("employee", employeeService.employeeLogin());
        return "index";
    }
    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin(){
        return "admin/admin";
    }

    @RequestMapping("/permission")
    public String getPermission(){
        return "leave-request";
    }

    @RequestMapping("/overtime")
    public String getOvertime(){
        return "overtime-request";
    }

    @RequestMapping("/profile")
    public String profilePage(){
        return "pages-profile";
    }
}
