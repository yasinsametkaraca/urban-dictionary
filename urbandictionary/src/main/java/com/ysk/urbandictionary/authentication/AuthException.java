package com.ysk.urbandictionary.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)  //401 döner.
public class AuthException extends RuntimeException{

}
