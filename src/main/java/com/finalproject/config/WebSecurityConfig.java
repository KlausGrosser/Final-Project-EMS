package com.finalproject.config;

import com.finalproject.model.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configure paths allowed for different users authorities and configure user authentication method
 */

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserService userService,
                             AccessDeniedHandler accessDeniedHandler,
                             PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.passwordEncoder = passwordEncoder;
    }


        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/js/**", "/images/**", "/css/**", "/index", "/", "/access-denied", "/favicon.ico", "/error/**", "/confirm", "/leaves/**")
                .permitAll()
                .antMatchers("/companies/**", "/company/**" , "/profile")
                .hasAuthority("SUPERADMIN")
                .antMatchers("/users/**", "/registration/**")
                .hasAuthority("HR_AGENT")
                .antMatchers("/shifts/**", "/activities/request", "/activities/add", "/activities/delete/**", "/activities/edit/**", "/activities/request/approve/**", "/activities/request/reject/**")
                .hasAuthority("SUPERVISOR")
                .antMatchers("/user/delete/{id}")
                .hasAuthority("HR_SUPERVISOR")
                .antMatchers("/check_in_out/**", "/currency/**",  "/profile", "/activities", "/activities/mark-time/**", "/activities/request/add/**", "/activities/request/complete/**", "/change_password", "/user/profileUpdate")
                .hasAuthority("USER")
                .antMatchers("/login")
                .anonymous()
                .anyRequest()
                .denyAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .defaultSuccessUrl("/profile")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
}
