package com.ysk.urbandictionary.shared;

import com.ysk.urbandictionary.user.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })   //nerde kullanağımızı belirlemek için
@Retention(RUNTIME)
@Constraint(validatedBy = {ProfileImageValidator.class })
public @interface ProfileImage {

    String message() default "{urbandictionary.constraint.ProfileImage.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
