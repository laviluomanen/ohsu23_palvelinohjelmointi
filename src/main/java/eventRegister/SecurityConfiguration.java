//This Class was taken directly as is from https://web-palvelinohjelmointi-21.mooc.fi/osa-5/2-autentikaatio-ja-auktorisointi

package eventRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // poistetaan csrf-tarkistus käytöstä h2-konsolin vuoksi
        http.csrf().disable();
        // sallitaan framejen käyttö
        http.headers().frameOptions().sameOrigin();

        //One can open /users path and add a new use or modify existing ones
        http.authorizeRequests()
                .antMatchers("/users","/users/**").permitAll()
                .antMatchers("/h2-console","/h2-console/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().permitAll().and()
                .logout().permitAll();
    }
    //Placeholder in case authentication needs to be removed for a while
    //@Override
    //public void configure(WebSecurity sec) throws Exception {
    //    sec.ignoring().antMatchers("/**");
    //}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}