package com.virtusa.mainpack.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.virtusa.mainpack.controller.Admincontroller;
import com.virtusa.mainpack.exception.InvaidDetails;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {
    
    @Autowired
    JwtUtil jwtutil;
    
    @Autowired
    UserDetailsService uservice;



    //this method is to filter requests according authorization
   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        String fulltoken= request.getHeader("Authorization");
        String uname=null;
        String token;
        
        //checking if token is null and extracting token
        if(fulltoken !=null && fulltoken.startsWith("Bearer "))
        {
            token = fulltoken.substring(7);
            try
            {
            uname=jwtutil.extractUsername(token);  
            }
            
            catch(Exception e)
            {
            	throw new InvaidDetails("noo token entered");
                //e.printStackTrace();
            }
            
            UserDetails uds = uservice.loadUserByUsername(uname);
            
            //checks whether the token is valid or not 
            if(uname!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                UsernamePasswordAuthenticationToken unamePwdAuthToken =
                        new UsernamePasswordAuthenticationToken(uds,null,uds.getAuthorities());
                unamePwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(unamePwdAuthToken);
            }
            else
            {
                Admincontroller.log.info("Token is not validated ");    
            }
        
        }
        
        filterChain.doFilter(request, response);
        
       
        
    }
}
