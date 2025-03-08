package com.dw3c.infocollecttool.controller;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.service.IInfoCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IInfoCollectionService infoCollectionService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/summary")
    public String info(Model model) {
        List<InfoCollection> list = infoCollectionService.getAll();
        model.addAttribute("list", list);
        return "summary";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        return "upload";
    }
}
