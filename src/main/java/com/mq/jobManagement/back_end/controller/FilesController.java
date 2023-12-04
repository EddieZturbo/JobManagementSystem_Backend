package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.pojo.FileInfo;
import com.mq.jobManagement.back_end.service.FilesStorageService;
import com.mq.jobManagement.back_end.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static com.mq.jobManagement.back_end.utils.ResultCode.*;

/**
 @author EddieZhang
 @create 2023-11-26 10:54 PM
 */
@RestController
public class FilesController {
    @Value("${file.download-url}")
    private String downloadUrl;
    @Autowired
    FilesStorageService storageService;

    @PostMapping("/upload/{jobId}")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("jobId") String jobId) {
        String message = "";
        try {
            storageService.save(file,jobId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return Result.ok(message);
        } catch (Exception e) {
            return Result.error(COULD_NOT_UPLOAD_FILE);
        }
    }

    @GetMapping("/files")
    public Result<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return Result.ok(fileInfos);
    }

    /**
     * 根据id获取文件的url和name
     * @param filename
     * @return
     */
    @GetMapping("/file/{filename:.+}")
    public Result<FileInfo> getFile(@PathVariable String filename) {
        String fileFullName = filename + ".pdf";
        return Result.ok(new FileInfo(fileFullName,downloadUrl + fileFullName));
    }

    @DeleteMapping("/files/{filename:.+}")
    public Result deleteFile(@PathVariable String filename) {
        String message = "";
        try {
            boolean existed = storageService.delete(filename);
            if (existed) {
                message = "Delete the file successfully: " + filename;
                return Result.ok(message);
            }
            return Result.error(THE_FILE_DOES_NOT_EXIST);
        } catch (Exception e) {
            message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
            return Result.error(COULD_NOT_DELETE_FILE,message);
        }
    }
}
