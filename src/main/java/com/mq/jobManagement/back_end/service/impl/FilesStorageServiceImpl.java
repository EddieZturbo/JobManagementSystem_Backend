package com.mq.jobManagement.back_end.service.impl;

import com.mq.jobManagement.back_end.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 @author EddieZhang
 @create 2023-11-26 10:50 PM
 */
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path getRoot() {
        return Paths.get(uploadDir);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(getRoot());
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.getRoot().resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = getRoot().resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            Path file = getRoot().resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(getRoot().toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.getRoot(), 1).filter(path -> !path.equals(this.getRoot())).map(this.getRoot()::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
