package com.mcc72.ClientKelompok5.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @RequestMapping
    public String redirectToDashboard(){
        return "redirect:/dashboard";
    }

    @RequestMapping("/dashboard")
    public String getDashboard(){
        return "index";
    }

    @RequestMapping("/admin")
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
