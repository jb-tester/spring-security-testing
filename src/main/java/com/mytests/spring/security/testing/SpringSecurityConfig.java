package com.mytests.spring.security.testing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SpringSecurityConfig  {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("vip").password("password").authorities("ROLE_VIP").build());
        manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
        return manager;
    }

    @Configuration
    public static class SecurityConfig extends WebSecurityConfigurerAdapter{
        protected void configure(HttpSecurity http) throws Exception {

                http
                        //HTTP Basic authentication
                        .httpBasic()
                        .and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER","VIP")
                        .antMatchers(HttpMethod.GET, "/users").hasAnyRole("USER","VIP")
                        .antMatchers(HttpMethod.GET, "/adm").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/adm/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/vip/**").hasAnyRole("ADMIN","VIP")
                        .antMatchers(HttpMethod.GET, "/vip/**").hasAnyAuthority("ROLE_ADMIN","ROLE_VIP")
                        .and()
                        .csrf().disable()
                        .formLogin().disable();
            }
        }
    }

