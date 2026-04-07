package com.project.service;

import com.project.model.FileEntity;
import com.project.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileEntity uploadFile(FileEntity file) {
        return fileRepository.save(file);
    }

    public List<FileEntity> getFilesByTask(Long taskId) {
        return fileRepository.findByTaskId(taskId);
    }

    public List<FileEntity> getFilesByUser(Long userId) {
        return fileRepository.findByUploadedById(userId);
    }

    public FileEntity getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}