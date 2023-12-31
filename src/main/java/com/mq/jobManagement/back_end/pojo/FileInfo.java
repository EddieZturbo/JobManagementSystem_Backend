package com.mq.jobManagement.back_end.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author EddieZhang
 @create 2023-11-26 10:40 PM
 */
@Data
@NoArgsConstructor
public class FileInfo {
    private String StudentName;
    private String fileName;
    private String url;

    public FileInfo(String fileName, String url,String studentName) {
        this.fileName = fileName;
        this.url = url;
        this.StudentName = studentName;
    }
}
