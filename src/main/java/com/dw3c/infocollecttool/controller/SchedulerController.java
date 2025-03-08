package com.dw3c.infocollecttool.controller;

import com.dw3c.infocollecttool.service.IDynamicSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private IDynamicSchedulerService dynamicSchedulerService;

    @GetMapping("/start")
    public String startScheduler() {
        dynamicSchedulerService.startScheduler();
        return "调度启动成功";
    }

    @GetMapping("/stop")
    public String stopScheduler() {
        dynamicSchedulerService.stopScheduler();
        return "调度停止成功";
    }
}