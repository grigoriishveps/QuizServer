package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    CustomDetailsService customDetailsService;

        @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/", "/login","/api/register", "/actuator/shutdown")
                .permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .httpBasic();
    }


    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


}


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
//
//    @Autowired
//    //DataSource dataSource;
//            CustomDetailsService customDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/register", "/actuator/shutdown")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .httpBasic();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//}
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/register", "/actuator/shutdown")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .httpBasic();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select email as username, password, 'true' as enabled"
//                        + " from user where email = ?")
//                .authoritiesByUsernameQuery("select email as username, 'user' as authority "
//                        + "from user where email = ?")
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
//}




//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://domain2.com")
//                .allowedMethods("PUT", "DELETE")
//                .allowedHeaders("header1", "header2", "header3")
//                .exposedHeaders("header1", "header2")
//                .allowCredentials(false).maxAge(3600);
//    }

