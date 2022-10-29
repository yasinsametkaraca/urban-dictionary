package com.ysk.urbandictionary.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ysk.urbandictionary.shared.Views;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) //verilen cevaplarda null olanları getirmez.
@Data
public class ApiError {

    @JsonView(Views.Base.class)
    private int status;
    @JsonView(Views.Base.class)
    private String message;
    @JsonView(Views.Base.class)
    private String path;  //hangi urlde

    @JsonView(Views.Base.class)
    private String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

    private Map<String,String> validationErrors;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
