package com.ysk.urbandictionary.shared;

import com.ysk.urbandictionary.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProfileImageValidator implements ConstraintValidator<ProfileImage,String> {

    @Autowired
    FileService fileService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null || value.isEmpty()){ //her requestte image gelmek zorunda değil sadece display name değişebilir bu yüzden burayı düzenledik.
            return true;
        }
        String fileType = fileService.detechType(value);
        if(fileType.equalsIgnoreCase("image/jpeg") || fileType.equalsIgnoreCase("image/png") || fileType.equalsIgnoreCase("image/jpg")  )
            return true;
        return false;
    }
}
