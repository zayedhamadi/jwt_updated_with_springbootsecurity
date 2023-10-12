package jwt_updated_with_springbootsecurity.Service;

import jwt_updated_with_springbootsecurity.Entity.User;
import jwt_updated_with_springbootsecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User opuser = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Not present "));
        return opuser;

    }
}
