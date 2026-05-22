package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectPageController {
    @GetMapping("/projektList")
    public String projektList() {
        return "projektList";
    }
}
