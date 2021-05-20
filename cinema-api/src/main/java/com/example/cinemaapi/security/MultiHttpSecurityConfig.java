package com.example.cinemaapi.security;

import com.example.cinemaapi.security.api.ApiJWTAuthenticationFilter;
import com.example.cinemaapi.security.api.ApiJWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

/**
 * .
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
        }

        // @formatter:off
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/api/v1/movies").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/payments/vnpay-return").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/categories").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/directors").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/languages").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/subtitles").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/cinemas").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/movie-rooms").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/movie-shifts").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/rooms").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/seats").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/seat-types").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/show-times").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/bills").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/bill-details").permitAll()
                    .antMatchers("/api/v1/auth/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                    .and()
                    .addFilter(new ApiJWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new ApiJWTAuthorizationFilter(authenticationManager()))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.cors();
        }
        // @formatter:on

    }
}
