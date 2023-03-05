package com.ysk.urbandictionary.authentication;

import lombok.Data;

@Data
public class Credentials { //login anındaki bilgileri almak için. Request için oluşturduk burayı.

    private String username;

    private String password;
}
