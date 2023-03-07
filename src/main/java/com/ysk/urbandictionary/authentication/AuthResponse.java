package com.ysk.urbandictionary.authentication;

import com.ysk.urbandictionary.user.dtos.UserDto;
import lombok.Data;

@Data
public class AuthResponse {  //Auth sonrası, hem user hemde token dönücek.

    private String token;

    private UserDto user;

}

/*dönen cevap.
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTgifQ.zNmtwThdj7WcGYVXxpcbvplYjewBsbYAw6rKCjro6qpghUUdaluNipZ1lCHXFXTvjB-Hs2gL3liRpIkQM6L_Zg",
        "user": {
            "username": "yasin",
            "displayName": "yasin",
            "image": null
        }
    }
*/
