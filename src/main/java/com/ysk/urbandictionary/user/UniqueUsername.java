package com.ysk.urbandictionary.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })   //nerde kullanağımızı belirlemek için
@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueUsernameValidator.class })
public @interface UniqueUsername {  //kendi annotationimizi oluşturucaz.

    String message() default "This username has been taken. Try another one";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
