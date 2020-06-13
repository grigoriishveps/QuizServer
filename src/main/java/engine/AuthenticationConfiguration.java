package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/register", "/actuator/shutdown")
                .permitAll().anyRequest().authenticated()
                .and().csrf().disable()
                .httpBasic();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email as username, password, 'true' as enabled"
                        + " from user where email = ?")
                .authoritiesByUsernameQuery("select email as username, 'user' as authority "
                        + "from user where email = ?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://domain2.com")
//                .allowedMethods("PUT", "DELETE")
//                .allowedHeaders("header1", "header2", "header3")
//                .exposedHeaders("header1", "header2")
//                .allowCredentials(false).maxAge(3600);
//    }
}
