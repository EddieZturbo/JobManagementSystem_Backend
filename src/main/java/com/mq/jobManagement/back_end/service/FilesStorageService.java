package com.mq.jobManagement.back_end.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 @author EddieZhang
 @create 2023-11-26 10:48 PM
 */
public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file, String jobId);

    public Resource load(String filename);

    public boolean delete(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();

    public String saveAndReturnId(MultipartFile file, String jobId);
}
