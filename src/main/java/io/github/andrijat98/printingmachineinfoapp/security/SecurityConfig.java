package io.github.andrijat98.printingmachineinfoapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.andrijat98.printingmachineinfoapp.filter.CustomAuthenticationFilter;
import io.github.andrijat98.printingmachineinfoapp.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig implements WebMvcConfigurer{
	
	private final UserDetailsService userDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    	//test later

        http.cors();
        
        http.authorizeRequests()
        .antMatchers("/user/token/refresh","/login","/user/getuser/**","/machines/all", "/user-photos/**").permitAll()
        .anyRequest().hasRole("ADMIN")
        
        //http.authorizeRequests()
        //.antMatchers("/user/*","/machines/get/*", "/machines/add", "/machines/update", "/machines/delete/**")
        //.hasRole("ADMIN")
        
        
        /*
        .antMatchers("/machines/add").hasRole("ADMIN")
        .antMatchers("/machines/update").hasRole("ADMIN")
        .antMatchers("/machines/delete/**").hasRole("ADMIN")*/
        
        
        
        .and().formLogin();
        
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        
        return http.build();
    }
    
    @Bean
    public AuthenticationProvider configureAuthentication() throws Exception {
    	
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userDetailService);
    	provider.setPasswordEncoder(bCryptPasswordEncoder);
    	return provider;
    }
    
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	
    	return new ProviderManager(configureAuthentication());
    }
}
