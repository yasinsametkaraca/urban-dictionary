package com.ysk.urbandictionary.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserAuthService userAuthService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint()); //auth fail olduğunda yönetebilmek için.

        http.headers().frameOptions().disable();  //h2 console erişmek için yaptık.

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/api/users/{username}").authenticated() //bunlar için security devreye girer token ister.
                .antMatchers(HttpMethod.POST,"/api/entries").authenticated()
                .antMatchers(HttpMethod.POST,"/api/entry-attachments").authenticated()
            .and()
            .authorizeRequests().anyRequest().permitAll();  //auth a gelenlerde authentication yap,bunların dışındakilere izin ver.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //security ile ilgili session üretimini yapmaz.

        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);  //gelen bütün requestlerde bizim filter metodumuz  da çalışacak. UsernamePasswordAuthenticationFilter dan önce bizim koyduğumuz requeste bakılıcak. Yani araya token sorgulaması için kendi filterimizi yazdık.
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());   //spring security e bir user arıyorsan UserAuthService i kullan. onun içinde loadUserByUsername ile databasede arayabilirsin. ve BCryptPasswordEncoder i kullandığımızı söylüyuruz.
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }  //şifreyi hashlemek için.

    @Bean
    TokenFilter tokenFilter(){                                  //tokenfilterin instance inin oluşturulması için
        return new TokenFilter();
    }
}
