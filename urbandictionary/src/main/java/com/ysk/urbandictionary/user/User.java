package com.ysk.urbandictionary.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

//hibernate java objectleri ile onlara karşılık gelen database tabloları arası ilişki sağlar
@Data
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue //hibernate otomatik olarak id atar.
    private long id;

    /*@JsonView(Views.Base.class)  //sadece görünmesini istediğimiz fieldlara ekledik. mesela password e eklemedik. fronendde passwordun gitmesini istemeyiz.*/
    @NotNull
    @Size(min = 3,max = 32)
    @UniqueUsername  //kendi annotationumuzu oluşturduk.
    private String username;

    @NotNull
    @Size(min = 3,max = 52)
    private String displayName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{urbandictionary.constraint.password.Pattern.message}" )
    @Size(min = 8,max = 84)
    @NotNull
    private String password;

    private String image;

    /* @JsonIgnore  //json dosyasında burayı gösterme.*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_user");  //spring security rol vermemizi istiyor.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
