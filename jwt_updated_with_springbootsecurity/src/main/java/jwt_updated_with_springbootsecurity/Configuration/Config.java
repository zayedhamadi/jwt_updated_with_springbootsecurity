package jwt_updated_with_springbootsecurity.Configuration;


import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User
//                .builder()
//                .username("zayed")
//                .password(passwordEncoder().encode("zayed"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user1 = User
//                .builder()
//                .username("zayedh80")
//                .password(passwordEncoder().encode("zayedh80"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,user1);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
