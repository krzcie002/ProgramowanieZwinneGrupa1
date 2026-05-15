package com.project.controller;

import com.project.model.FileEntity;
import com.project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public FileEntity uploadFile(@RequestBody FileEntity file) {
        return fileService.uploadFile(file);
    }

    @GetMapping("/{id}")
    public FileEntity getFile(@PathVariable Integer id) {
        return fileService.getFileById(id);
    }

    @GetMapping("/task/{taskId}")
    public List<FileEntity> getFilesByTask(@PathVariable Integer taskId) {
        return fileService.getFilesByTask(taskId);
    }

    @GetMapping("/user/{userId}")
    public List<FileEntity> getFilesByUser(@PathVariable Integer userId) {
        return fileService.getFilesByUser(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Integer id) {
        fileService.deleteFile(id);
    }
}