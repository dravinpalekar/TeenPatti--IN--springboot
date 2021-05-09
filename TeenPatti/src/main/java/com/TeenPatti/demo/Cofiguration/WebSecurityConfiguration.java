package com.TeenPatti.demo.Cofiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.TeenPatti.demo.Cofiguration.Util.JwtAuthenticationEntryPoint;
import com.TeenPatti.demo.Cofiguration.Util.JwtFilter;
import com.TeenPatti.demo.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableAsync
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
//	
	@Autowired
    private JwtFilter jwtFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {	
        http.csrf().disable()
        		.authorizeRequests()
        			.antMatchers("/helloadmin","/").hasRole("ADMIN")
        			.antMatchers("/hellouser").hasRole("USER")
//        			.antMatchers(HttpMethod.POST, "/notification").hasRole("USER")
//        			.antMatchers(HttpMethod.GET, "/searchByEmail").hasRole("USER")
//        			.antMatchers("/user/group").hasRole("USER")
//        			.antMatchers("/").hasRole("GUEST")
        			.antMatchers(HttpMethod.POST, "/register/user", "/authenticate", "/test").permitAll().anyRequest().authenticated()
                
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        http.cors(); //for CORSS-Access Enabling
    }

}
