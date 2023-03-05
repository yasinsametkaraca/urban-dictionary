package com.ysk.urbandictionary.configuration;


import com.ysk.urbandictionary.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {           //gelen bütün requestler bu filtreden geçer. Gelen request içerisindeki authorization headera bakıp tokenin geçerli olup olmamasını sorgulucaz.

    @Autowired
    AuthService authService;

    @Override  //chain of responsibility pattern.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization != null){
            String token = authorization.substring(7); //tokeni aldık requestten

            UserDetails user = authService.getUserDetails(token);  //bu tokendan user bilgisini alıcaz kime ait bir token olduğunu öğrenicez.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());           //spring security ile biraraya getirdik.
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //artık authentication obejsi olarak da bizim userin bilgilerinin barındırıldığı object kullanılacak.
        }
        filterChain.doFilter(request,response);
    }

}
