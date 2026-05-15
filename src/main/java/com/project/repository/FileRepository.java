package com.project.repository;

import com.project.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    List<FileEntity> findByTaskId(Integer taskId);

    List<FileEntity> findByUploadedById(Integer userId);
}