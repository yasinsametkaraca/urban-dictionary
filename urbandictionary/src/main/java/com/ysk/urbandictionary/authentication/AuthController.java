package com.ysk.urbandictionary.authentication;

import com.ysk.urbandictionary.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/auth")  //login için burayı yazdım.
    AuthResponse handleAuthentication(@RequestBody Credentials credentials) {
        return authService.authenticate(credentials);
    }

    @PostMapping("/api/logout")  //logout olduğumuzda tokeni db den silicek.
    GenericResponse handleLogout(@RequestHeader(name = "Authorization") String authorization) {  //bu kullanıcının mobilde tablette login olmuşluğu vardır hepsinden aynanda logout olmak istemez bu yüzden ona göre kontrol ettik. RequestHeader diyerek gelen isteğin headeranda Authorizationu alıp yani tokeni alıp dbden silicez. Yani direk o kullancıya ait olan bütün tokenları silmiyoruz.
        String token = authorization.substring(7); //böyle yapmamızın sebebi frontendde başında bearer yazarak yolluyoruz. İstesek öyle yapmayız.
        authService.clearToken(token);
        return new GenericResponse("Logout Success");
    }

}


