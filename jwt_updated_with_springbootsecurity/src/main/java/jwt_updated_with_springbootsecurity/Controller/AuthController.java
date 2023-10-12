package jwt_updated_with_springbootsecurity.Controller;


import jwt_updated_with_springbootsecurity.Entity.JwtRequest;
import jwt_updated_with_springbootsecurity.Entity.JwtResponse;
import jwt_updated_with_springbootsecurity.Entity.User;
import jwt_updated_with_springbootsecurity.Exception.UserException;
import jwt_updated_with_springbootsecurity.Security.JwtHelp;
import jwt_updated_with_springbootsecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelp helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        String token=this.helper.generateToken(userDetails);
        JwtResponse response=JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();

        return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) throws UserException {

        String message=userService.addUser();

        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }


    private void doAuthenticate(String username,String password) {
        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(username, password);

        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or password");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}