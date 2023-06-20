package com.virtusa.mainpack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class Securityconfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserserviceImpl userdtlser;
	
	@Autowired
	JwtFilterRequest jwtfilter;
	
	@Autowired
	JwtAuthfilteEntry entrypoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userdtlser).passwordEncoder(pswdencoder());
		
	}

	//authorizing requests according to roles
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/admincontroller/**").permitAll()
		.antMatchers("/customer/**").hasRole("CUSTOMER")
		.antMatchers("/home/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint(entrypoint);
		
		http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	//to encrypt the user password
	@Bean
	public BCryptPasswordEncoder pswdencoder()
	{
		
		return new BCryptPasswordEncoder();
				
	}
	
	//to authenticate by log in details
	@Bean
	public AuthenticationManager authmanagebean() throws Exception
	{
		return super.authenticationManager();
				
	}
	
	 
	

}
