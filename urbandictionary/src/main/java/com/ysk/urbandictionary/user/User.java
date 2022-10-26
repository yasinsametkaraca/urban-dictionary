package com.ysk.urbandictionary.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//hibernate java objectleri ile onlara karşılık gelen database tabloları arası ilişki sağlar
@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue //hibernate otomatik olarak id atar.
    private long id;

    @NotNull
    @Size(min = 3,max = 32)
    @UniqueUsername  //kendi annotationumuzu oluşturduk.
    private String username;

    @NotNull
    @Size(min = 3,max = 52)
    private String displayName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    @Size(min = 8,max = 84)
    @NotNull
    private String password;


}
