package com.mcc72.ClientKelompok5.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class HistoryController {

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
