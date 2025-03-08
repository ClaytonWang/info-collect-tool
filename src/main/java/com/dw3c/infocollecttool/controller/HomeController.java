package com.dw3c.infocollecttool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/summary")
    public String info(Model model) {
        return "summary";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        return "upload";
    }
}
