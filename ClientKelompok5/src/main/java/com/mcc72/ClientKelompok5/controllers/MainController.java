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


    @RequestMapping("/dashboard/admin")
    public String getDashboardAdmin(){
        return "admin/admin";
    }
    @RequestMapping("/dashboard/manager")
    public String getDashboardManager(){
        return "manager/manager";
    }

    @RequestMapping("/permission")
    public String getPermission(){
        return "leave-request";
    }

    @RequestMapping("/overtime")
    public String getOvertime(){
        return "overtime-request";
    }

    @RequestMapping("/history")
    public String getHistory(){
        return "history/history";
    }


    @RequestMapping("/history/permission")
    public String getHistoryPermission(){
        return "history/history-leave-request";
    }


    @RequestMapping("/history/overtime")
    public String getHistoryOvertime(){
        return "history/history-overtime-request";
    }


}
