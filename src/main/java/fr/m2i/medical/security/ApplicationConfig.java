package fr.m2i.medical.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dsource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        // Solution 1 : auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN");

        //Solution 2:
        //System.out.println( passwordEncoder().encode("1234") );
        // 1. récupère l'utilisateur, 2.vérifie le pass en utilisant l'encodeur
        /* auth.jdbcAuthentication().dataSource( dsource )
                .usersByUsernameQuery( "SELECT username, password, 1 enabled FROM user WHERE username = ?" )
                .authoritiesByUsernameQuery( "SELECT username, roles FROM user WHERE username = ?" )
                .passwordEncoder( passwordEncoder() );*/


        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //utiliser un form de login - login url=/login
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");

        http.authorizeRequests().antMatchers("/login","/css/**","/images/**").permitAll();

        //les action POST sont autorisé que pour ADMIN
        http.authorizeRequests().antMatchers("**/add","**/edit/**","**/delete/**").hasRole("ADMIN");

        http.authorizeRequests().anyRequest().authenticated();

        http.csrf().disable();
    }
}