package com.campaign.admission.configuration;

import com.campaign.admission.configuration.handler.AuthenticationHandler;
import com.campaign.admission.configuration.handler.WrongAuthenticationHandler;
import com.campaign.admission.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationHandler handler;
    private final WrongAuthenticationHandler exceptionHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/",
                            "/api/home",
                            "/api/login",
                            "/api/register",
                            "/api/error")
                        .permitAll()
                    .antMatchers("/api/student")
                        .hasAuthority("STUDENT")
                    .antMatchers("/api/admin")
                        .hasAuthority("ADMIN")
                    .anyRequest()
                        .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/api/login")
                    .successHandler(handler)
                    .failureHandler(exceptionHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                        .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                    .logoutSuccessUrl("/api/home")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/api/home");
    }
}
