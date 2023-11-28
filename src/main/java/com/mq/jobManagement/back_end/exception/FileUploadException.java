package com.mq.jobManagement.back_end.exception;

import com.mq.jobManagement.back_end.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.mq.jobManagement.back_end.utils.ResultCode.FILE_TOO_LARGE;

/**
 @author EddieZhang
 @create 2023-11-26 10:44 PM
 */
@RestControllerAdvice
public class FileUploadException extends ResponseEntityExceptionHandler{
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return Result.error(FILE_TOO_LARGE);
    }
}
