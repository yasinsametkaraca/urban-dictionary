package com.ysk.urbandictionary.authentication;


import com.fasterxml.jackson.annotation.JsonView;
import com.ysk.urbandictionary.shared.CurrentUser;
import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserRepository;
import com.ysk.urbandictionary.user.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/auth")
    UserDto handleAuthentication(@CurrentUser User user) {       //RequestHeader in mantığı gelen isteğin header bilgisini(Authorization bilgisi) almak. required yazarak frontendden o bilgiyi zorunlu bir şekilde getirmesini reddettik.
        return new UserDto(user);  //frontende dönüyoruz
    }


    /*@PostMapping("/api/auth")
    ResponseEntity<?> handleAuthentication(@RequestHeader(name="Authorization") String authorization) {   //RequestHeader in mantığı gelen isteğin header bilgisini(Authorization bilgisi) almak. required yazarak frontendden o bilgiyi zorunlu bir şekilde getirmesini reddettik.
        if(authorization == null){
            ApiError error = new ApiError(400,"Unauthorized request","api/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        String base64encoded = authorization.split("Basic ")[1];  //Basic MTAzMDExMTgwN0BlcmNpeWVzLmVkdS50cnNkZnNmOnNmc2Rmc2RzZnNn  //frontend den hash li geliyor.
        String decoded = new String(Base64.getDecoder().decode(base64encoded));  //username ve password u decode ettik. username:password şeklinde geldi bilgi
        String[] parts =decoded.split(":");
        String username = parts[0];
        String password = parts[1];
        User inDatabase = userRepository.findByUsername(username);
        if (inDatabase == null) {
            ApiError error = new ApiError(400,"Unauthorized request","api/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        String hashedPassword = inDatabase.getPassword();  //veritabanında hash li tutuluyor.
        if(!passwordEncoder.matches(password,hashedPassword)){
            ApiError error = new ApiError(400,"Unauthorized request","api/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        //username,displayName,image
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("username",inDatabase.getUsername());
        responseBody.put("displayName",inDatabase.getDisplayName());
        responseBody.put("image",inDatabase.getImage());

        return ResponseEntity.ok(responseBody);  //frontende dönüyoruz
    }*/

   /* @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ApiError handleBadCredentialsException(){
        ApiError error = new ApiError(401,"Unauthorized request","/api/auth");
        return error;
    }*/

}

