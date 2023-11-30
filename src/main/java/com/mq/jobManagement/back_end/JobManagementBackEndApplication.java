package com.mq.jobManagement.back_end;

import com.mq.jobManagement.back_end.service.FilesStorageService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan(basePackages = "com.mq.jobManagement.back_end.mapper")
@ComponentScan(basePackages = {"com.mq.jobManagement.back_end.*"})
public class JobManagementBackEndApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(JobManagementBackEndApplication.class, args);
    }
    @Override
    public void run(String... arg) throws Exception {
//    storageService.deleteAll();
        storageService.init();
    }

}
