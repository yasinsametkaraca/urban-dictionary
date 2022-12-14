package com.ysk.urbandictionary.user;

import com.ysk.urbandictionary.shared.GenericResponse;
import com.ysk.urbandictionary.user.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Function;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/users")
    public GenericResponse createUser(@Valid @RequestBody User user){                                               //RequestBody in mantığı gelen requestin içerisindeki body i bize ver deriz.
        userService.save(user);
        return new GenericResponse("User created successfully");
    }

    //@JsonView                                                                                                     //passwordun vs. gelmemesi için.
    @GetMapping("/api/users")
    Page<UserDto> getAllUsers(Pageable page){                                                                        //url içinde istek atılanlar için requestParam kullanılır.
        return userService.getAllUsers(page).map(new Function<User, UserDto>() {                                     //page içindeki userları userdto ya çeviricez. User alıp UserDto dönülücek.
            @Override
            public UserDto apply(User user) {
                return new UserDto(user);                                                                           //userdto da constructorda tanımlayıp user tipindeki object e dönüştüren kod yazmış olduk.
            }
        });
    }

}









/*Page<User> getAllUsers(@RequestParam int currentPage, @RequestParam(required = false,defaultValue = "5") int pageSize){                           //url içinde istek atılanlar için requestParam kullanılır.
        return userService.getAllUsers(currentPage, pageSize);
}*/

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