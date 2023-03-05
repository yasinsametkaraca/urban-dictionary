package com.ysk.urbandictionary.authentication;

import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserRepository;
import com.ysk.urbandictionary.user.UserService;
import com.ysk.urbandictionary.user.dtos.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.Date;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(Credentials credentials) {                                                                                     //userin var olup olmadığınının kontrolü yapıcak.
        User inDatabase = userRepository.findByUsername(credentials.getUsername());
        if(inDatabase == null){
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(),inDatabase.getPassword());                                              //login requestindeki password ile dbdeki hashli passwordu karşılaştırdık.
        if(!matches){
            throw new AuthException();
        }
        UserDto userDto = new UserDto(inDatabase);                                                                                               //UserDtonun constructor u user objesi alır ordan UserDto objesi üretir.
        Date expiredAt =  new Date(System.currentTimeMillis() + 345600000);
        String token = Jwts.builder().setSubject("" + inDatabase.getId()).signWith(SignatureAlgorithm.HS512,"secret-key").setExpiration(expiredAt).compact();           // token oluşturucaz id ye göre olucak. signWith in mantığı hangi algoritma ve ikinci parametrede uygulamaya özel saklı tutulması gereken key bilgisi istiyor.
        AuthResponse response = new AuthResponse();
        response.setUser(userDto);
        response.setToken(token); //token ve user oluşturup bunu frontende dönüyoruz.
        return response;
    }

    @Transactional
    public UserDetails getUserDetails(String token) { //bu tokenin kime ait olduğunun bilgisini aldık.
        JwtParser parser = Jwts.parser().setSigningKey("secret-key");
        try {
            parser.parse(token);
            Claims claims = parser.parseClaimsJws(token).getBody();
            long userId = new Long(claims.getSubject());  //amacımız tokendan user id yi almak.
            User user = userRepository.getReferenceById(userId);
            User actualUser = (User)((HibernateProxy)user).getHibernateLazyInitializer().getImplementation();  //hibernate prox objesini user objesine dönüştürüyoruz.
            return actualUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
