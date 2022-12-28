package com.mcc72.ClientKelompok5.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    @RequestMapping
    public String index(){
        return "manager/manager";
    }

    @RequestMapping("/employee")
    public String managerEmployee(){
        return "manager/manager-employee";
    }


    @RequestMapping("/permission")
    public String managerPermission(){
        return "manager/manager-leave-request";
    }


    @RequestMapping("/overtime")
    public String managerOvertime(){
        return "manager/manager-overtime-request";
    }



    @RequestMapping("/project")
    public String managerProject(){
        return "manager/manager-project";
    }


}
