package example.config;

import example.model.*;
import example.ext.*;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private DataSource dataSource;

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  		http
  			.authorizeRequests()
        .antMatchers("/user").access("hasRole('USER') or hasRole('SUPER')")
        .antMatchers("/admin").access("hasRole('ADMIN') or hasRole('SUPER')")
        .antMatchers("/super").access("hasRole('SUPER')")
  			.antMatchers("/login", "/about", "/home")
          .permitAll()
  				.anyRequest().authenticated()
  				.and()
  			.formLogin()
  				.loginPage("/login")
          .loginProcessingUrl("/login")
          .successHandler(myAuthenticationSuccessHandler())
  				.permitAll();
//          .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
  	public PasswordEncoder passwordEncoder()
  	{
  		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
  	}

    @Bean
  	public example.ext.ExtUserDetailsService inMemoryUserDetailsManager()
  	{
  		return new example.ext.ExtUserDetailsService();
  	}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(inMemoryUserDetailsManager());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
  	@Override
  	public UserDetailsService userDetailsService() {
      example.ext.ExtUserDetailsService details = inMemoryUserDetailsManager();
      return details;
  	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager())
            .passwordEncoder(passwordEncoder())
            .and()
            .authenticationProvider(authenticationProvider())
            .jdbcAuthentication()
            .dataSource(dataSource);
    }

}
