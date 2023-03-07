package com.ysk.urbandictionary.user.dtos;

import com.ysk.urbandictionary.user.User;
import lombok.Data;

@Data
public class UserDto {          //view model // data transfer objects ile json oluşturucaz.

    private String username;

    private String displayName;

    private String image;

    public UserDto(User user) {  //user ı userdto ya dönüştürücez.
        this.setUsername(user.getUsername());
        this.setDisplayName(user.getDisplayName());
        this.setImage(user.getImage());
    }
}

