package com.project.controller;

import com.project.dto.ProjectCreateRequest;
import com.project.dto.ProjectDto;
import com.project.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProjectPageController {
    @GetMapping("/projectList")
    public String projectList() {
        return "projectList";
    }
    @GetMapping("/projectAdd")
    public  String projectAdd(Model model){
        model.addAttribute("projekt", new ProjectCreateRequest("", "",""));
        return "projectAdd";
    }
    @GetMapping("/projectEdit")
    public  String projectEdit(){
        return "projectEdit";
    }

    @GetMapping("/projectDetails")
    public String projectDetails() {
        return "projectDetails";
    }
}
