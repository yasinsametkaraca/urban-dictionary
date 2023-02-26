package com.ysk.urbandictionary.user.dtos;

import com.ysk.urbandictionary.shared.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDto {

    @NotNull
    @Size(min = 3,max = 52)
    private String displayName;

    @FileType(types={"jpg","png","jpeg"})  //biz oluşturduk shared içinde vardır.
    private String image;

}
