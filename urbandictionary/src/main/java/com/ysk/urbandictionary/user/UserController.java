package com.ysk.urbandictionary.user;

import com.ysk.urbandictionary.error.ApiError;
import com.ysk.urbandictionary.shared.CurrentUser;
import com.ysk.urbandictionary.shared.GenericResponse;
import com.ysk.urbandictionary.user.dtos.UserDto;
import com.ysk.urbandictionary.user.dtos.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Function;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public GenericResponse createUser(@Valid @RequestBody User user){                                               //RequestBody in mantığı gelen requestin içerisindeki body i bize ver deriz.
        userService.save(user);
        return new GenericResponse("User created successfully");
    }

    //@JsonView                                                                                                     //passwordun vs. gelmemesi için.
    @GetMapping("/users")
    Page<UserDto> getAllUsers(Pageable page, @CurrentUser User user){                                                                        //url içinde istek atılanlar için requestParam kullanılır. CurrentUser annotation o anda login olan kullanıcıyı vericek.
        return userService.getAllUsers(page,user).map(new Function<User, UserDto>() {                                     //page içindeki userları userdto ya çeviricez. User alıp UserDto dönülücek.
            @Override
            public UserDto apply(User user) {
                return new UserDto(user);                                                                           //userdto da constructorda tanımlayıp user tipindeki object e dönüştüren kod yazmış olduk.
            }
        });
    }
    @GetMapping("/users/{username}")
    UserDto getUserByUsername(@PathVariable String username) {                                                      //PathVariable diyerek url içinde (/users/{username}) bu parametreyi kullanıcaz diyoruz.
        User user =  userService.getByUsername(username);                                                                   //servisler arası iletişimde dto yerine user class ı kullanmak daha mantıklıdır. (bu servis classları başka servis classları ile kullanılabilir. )
        return new UserDto(user);                                                                                       //user objectinden userdto yarattık. userdto olarak frontende dönüyoruz
    }

    @PutMapping("/users/{username}")
    @PreAuthorize("#username == principal.username") //bu metoda gelmeden önce authorize yap. Eğer parantez içindekiler doğruysa girer metodun içine. Principal logged olan kullanıcıyı temsil eder.
    UserDto updateUser(@RequestBody UserUpdateDto updatedUser, @PathVariable String username) {
        User user = userService.updateUser(username,updatedUser);
        return new UserDto(user);
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