package com.ysk.urbandictionary.error;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Data
public class ApiError {

    private int status;
    private String message;
    private String path;  //hangi urlde
    private String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    private Map<String,String> validationErrors;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
