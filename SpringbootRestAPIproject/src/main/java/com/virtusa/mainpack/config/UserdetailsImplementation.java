package com.virtusa.mainpack.config;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.virtusa.mainpack.entity.Userlogdetails;

public class UserdetailsImplementation implements UserDetails {

	
	
	private static final long serialVersionUID = 1L;
	private Userlogdetails admobj;
	
	//constructor as login details object
	public UserdetailsImplementation(Userlogdetails admobj)
	{
		this.admobj = admobj;
	}
	
	//to grant authority bases on roles
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		HashSet<SimpleGrantedAuthority> set = new HashSet<>();
		set.add(new SimpleGrantedAuthority(admobj.getRoles().getRolename()));
		return set;
	}

	//authenticate user password
	@Override
	public String getPassword() {
		
		return admobj.getUserpassword();
	}
	//authenticate users username
	@Override
	public String getUsername() {
		
		return admobj.getUsername();
	}

	//to check whether the account is expired or not
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	//to check whether the account is locked or not
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	//to check whether the credentials are not expired or not
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
