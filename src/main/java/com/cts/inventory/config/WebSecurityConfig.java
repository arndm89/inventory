/**
 * 
 */
package com.cts.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author ctsjavafsd24
 *
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}password").roles("ADMIN","USER")
            .and()
            	.withUser("arindam")
            	.password("{noop}password").roles("USER");
    }
	
	@Override
   protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
        .and()
        	.csrf().disable()
        	.authorizeRequests()
        .antMatchers("/item/get/**").permitAll()
        .antMatchers("/item/create","/item/update", "/item/delete/**").hasRole("ADMIN");
    }
  
   
	

}
