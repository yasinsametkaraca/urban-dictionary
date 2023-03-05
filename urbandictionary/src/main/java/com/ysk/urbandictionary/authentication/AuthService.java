package com.ysk.urbandictionary.authentication;

import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserRepository;

import com.ysk.urbandictionary.user.dtos.UserDto;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
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
        String token = generateRandomTokenName();
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(inDatabase);
        tokenRepository.save(tokenEntity);

        AuthResponse response = new AuthResponse();
        response.setUser(userDto);
        response.setToken(token); //token ve user oluşturup bunu frontende dönüyoruz.
        return response;
    }

    @Transactional
    public UserDetails getUserDetails(String token) { //bu tokenin kime ait olduğunun bilgisini aldık.
        Optional<Token> optionalToken = tokenRepository.findById(token);
        if(!optionalToken.isPresent()){
            return null;
        }
        return optionalToken.get().getUser();
    }

    public String generateRandomTokenName(){ //opaque kullandığımız için tokeni db ye kaydedicez.
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void clearToken(String token) {
        tokenRepository.deleteById(token);
    }
}
