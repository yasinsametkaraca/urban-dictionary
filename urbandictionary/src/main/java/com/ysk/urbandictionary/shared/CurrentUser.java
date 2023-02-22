package com.ysk.urbandictionary.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ PARAMETER })   //nerde kullanağımızı belirlemek için
@Retention(RUNTIME)
@AuthenticationPrincipal  //otomatik cast etme işlemi yapıcaz.
public @interface CurrentUser {  //login olan o anki kullanıcı otomatik cast edicek.

}
