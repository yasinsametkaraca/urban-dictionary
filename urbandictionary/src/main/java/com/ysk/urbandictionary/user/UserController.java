package com.ysk.urbandictionary.user;

import com.ysk.urbandictionary.error.ApiError;
import com.ysk.urbandictionary.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/users")
    public GenericResponse createUser(@Valid @RequestBody User user){    //RequestBody in mantığı gelen requestin içerisindeki body i bize ver deriz.
        userService.save(user);
        return new GenericResponse("User created successfully");
    }
    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError error = new ApiError(400,"Validation Error","api/users");
        Map<String,String> validationErrors = new HashMap<>();
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return error;
    }*/
}




  /*  ApiError error = new ApiError(400,"Validation Error","api/users");
    Map<String,String> validationErrors = new HashMap<>();

    String username = user.getUsername();
    String displayName = user.getDisplayName();

        if(username == null || username.isEmpty()){
                validationErrors.put("username","Username cannot be empty");
                }

                if(displayName == null || displayName.isEmpty()){
                validationErrors.put("displayName","Name cannot be empty");
                }
                if(validationErrors.size() > 0){
                error.setValidationErrors(validationErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }*/