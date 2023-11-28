package com.mq.jobManagement.back_end.utils;

/**
 @author EddieZhang
 @create 2023-11-13 9:53 AM
 */
public enum ResultCode {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    DATA_ABSENCE(410, "Data Absence"),
    ADD_DATA_FAILURE(411, "Add Data Failure"),
    UPDATE_DATA_FAILURE(412, "Update Data Failure"),
    DELETE_DATA_FAILURE(413, "Delete Data Failure"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    COULD_NOT_UPLOAD_FILE(498, "Could not upload the file"),
    COULD_NOT_DELETE_FILE(497, "Could not delete the file"),
    THE_FILE_DOES_NOT_EXIST(496, "The file does not exist"),
    FILE_TOO_LARGE(499, "File too large!");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
