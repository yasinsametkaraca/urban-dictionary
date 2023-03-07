package com.ysk.urbandictionary.shared;

import com.ysk.urbandictionary.file.FileService;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileType,String> {

    @Autowired
    FileService fileService;

    String[] types;  //Typesları direk anatosyan üzerinden alıcaz.

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null || value.isEmpty()){ //her requestte image gelmek zorunda değil sadece display name değişebilir bu yüzden burayı düzenledik.
            return true;
        }
        String fileType = fileService.detectType(value);
        for(String supportedType: this.types){  //her types değişkeni supportedType olarak adlandıralım.
            if(fileType.contains(supportedType)){  //Typesları direk anatosyan üzerinden alıcaz.
                return true;
            }
        }
        String supportedTypes = Arrays.stream(this.types).collect(Collectors.joining(", "));  //jpg,png şeklinde yazsın diye yazdık.
        context.disableDefaultConstraintViolation(); //default verilen mesajı engelle.
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
        hibernateConstraintValidatorContext.addMessageParameter("types", supportedTypes);  //ValidationMessages.propertieste {types olarak kullanacağımız yere supportedTypes da yazanları getirecek.}
        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();

        /*if(fileType.equalsIgnoreCase("image/jpeg") || fileType.equalsIgnoreCase("image/png") || fileType.equalsIgnoreCase("image/jpg")  )
            return true;*/
        return false;
    }
}
