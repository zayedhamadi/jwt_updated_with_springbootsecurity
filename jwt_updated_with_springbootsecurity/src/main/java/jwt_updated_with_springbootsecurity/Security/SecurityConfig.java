package jwt_updated_with_springbootsecurity.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

   @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http )throws Exception{
       http
               .csrf(csrf -> csrf
                       .disable()
                    )
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers("/user/**")
                       .authenticated()
                       .requestMatchers("/auth/login").
                       permitAll().
                       requestMatchers("/auth/add").permitAll().
                       anyRequest().authenticated()
                                        )
               .exceptionHandling(ex -> ex
                       .authenticationEntryPoint(point)

                                    )
               .sessionManagement(session -> session
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                    );

       http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
   }


   @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
       daoAuthenticationProvider.setUserDetailsService(userDetailService);
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
       return daoAuthenticationProvider;
   }



}