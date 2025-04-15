package com.example.paintapi.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.paintapi.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.var;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

    private final JwtUtil jwtUtil;
    private final UserDetailsService  userDetailsService;
    
     public JwtAuthenticationFilter(JwtUtil jwtUtil,UserDetailsService userDetailsService)
     {
         this.jwtUtil =jwtUtil;
         this.userDetailsService =userDetailsService;
         
     }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeaderString =request.getHeader("Authorization");
        System.out.println("[JWT] Authorization Header = " + authHeaderString);
        
        if(authHeaderString !=null && authHeaderString.startsWith("Bearer "))
        {
            String jwt =authHeaderString.substring(7).trim();
            try {
                if(!jwt.contains("."))
                {

                    System.out.println("[JWT] Invalid JWT: not enough dots");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT format.");
                    return;
                }
                String usernameString =jwtUtil.extractUsername(jwt);
                System.out.println("[JWT] Username = " + usernameString);
                
                if(usernameString !=null && SecurityContextHolder.getContext().getAuthentication() ==null)
                {
                    var userDetails = userDetailsService.loadUserByUsername(usernameString);
                    if (jwtUtil.validateToken(jwt)) 
                    {
                        var auth =new UsernamePasswordAuthenticationToken
                        (userDetails,null,userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }catch (Exception e) {
                System.err.println("[JWT] Error parsing JWT: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token.");
                return;
                // TODO: handle exception
            }
                      
            
            
        }
    
        filterChain.doFilter(request, response);
    }
}