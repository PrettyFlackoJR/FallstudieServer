package dhbw.server;

import dhbw.server.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/vorlesungsplaner/admin/**").hasAuthority("ADMIN")
                .antMatchers("/vorlesungsplaner/termin_add").hasAuthority("EDITOR")
                .antMatchers("/vorlesungsplaner/**").hasAnyAuthority("ADMIN", "USER", "STUDENT")
                .antMatchers("/css/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .csrf().ignoringAntMatchers("/**")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/vorlesungsplaner")
                .failureHandler((request, response, exception) -> {
                    String redirectUrl = request.getContextPath() + "/login?error";
                    response.sendRedirect(redirectUrl);
                })
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessdenied");
    }


}
