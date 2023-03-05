package com.ysk.urbandictionary.authentication;

import com.ysk.urbandictionary.user.User;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Token {  //monolitik uygulamalarda tokeni db de tutmak daha mantıklıdır. //opaque token kullanıcız jwt yerine.

    @Id //tokenları unique yapmış olduk aynı zamanda.
    private String token;

    @ManyToOne  //bir user birden fazla clientte login olabilir. Yani bir userin birden fazla tokeni olabilir.
    private User user;

    //ilerde tokena expire, hangi clientten alındığı özelliği eklicem.
}
