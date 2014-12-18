package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("user").roles("USER")
				.and()
				.withUser("admin").password("admin").roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/VAADIN/**", "/PUSH/**", "/UIDL/**", "/login", "/login/**", "/logout").permitAll()
				.antMatchers("/**").authenticated()
				
			.and()
			
			.csrf().disable()
			
			.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
				
			.and()
			
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.permitAll();
	
		/*
		http
			.formLogin()
				.failureUrl("/login?error")
				.defaultSuccessUrl("/")
				.loginPage("/login")
				.loginProcessingUrl("/authorize")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
			
			.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.permitAll();
			
		http
			.csrf().disable();
		
		http.authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers("/authorize").permitAll()
		.antMatchers("/spring_security_login").permitAll()
		.antMatchers("/VAADIN/*").permitAll()
		.antMatchers("/VAADIN/**").permitAll()
		.antMatchers("/UIDL/*").permitAll()
		.antMatchers("/UIDL/**").permitAll();
		
		*/
	}
	
	@Override
	@Bean(name = "authenticationManager")
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
