package com.usuario.departamento_usuario.security;

import com.usuario.departamento_usuario.repositories.UserRepository;
import com.usuario.departamento_usuario.security.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //request.getServletPath() gets the endpoint that the requisition is calling for
        if(!request.getServletPath().equals("/auth/login") && !request.getServletPath().equals("/auth/register")) {

            String token = this.recoverToken(request);

            if (token != null) {
                String email = tokenService.validateToken(token);
                UserDetails user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken
                            (user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest servletRequest){
        String authHeader = servletRequest.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
