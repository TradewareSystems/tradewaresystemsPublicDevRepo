package com.tradeware.securelogin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tradeware.stockmarket.bean.UserDataBean;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Service
public class SecurityLoginUserService implements UserDetailsService {
	@Autowired
	private DatabaseHelper databaseHelper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// System.out.println(
		// ">>>>>>>>>>>>loadUserByUsername>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
		// + username);
		UserDetails userDetails = null;
		UserDataBean userDataBean = databaseHelper.getUserDataBean(username);
		if (null == userDataBean) {
			throw new UsernameNotFoundException("User not found.");
		} else {
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			userDetails = new org.springframework.security.core.userdetails.User(userDataBean.getUserId(),
					userDataBean.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
					getAuthorities(/* userDataBean.getRoles() */null));
		}
		return userDetails;
	}

	private static List<GrantedAuthority> getAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN_ROLE"));
		authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
		return authorities;
	}
}
