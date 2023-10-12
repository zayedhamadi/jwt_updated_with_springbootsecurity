package jwt_updated_with_springbootsecurity.Controller;


import jwt_updated_with_springbootsecurity.Entity.CurrentUserSession;
import jwt_updated_with_springbootsecurity.Entity.LogIn;
import jwt_updated_with_springbootsecurity.Entity.User;
import jwt_updated_with_springbootsecurity.Exception.CurrentUserException;
import jwt_updated_with_springbootsecurity.Exception.UserException;
import jwt_updated_with_springbootsecurity.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;





    @PutMapping("update/{uuId}/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User u ,@PathVariable ("uuId")String uuId) throws CurrentUserException {
            User user = this.userService.updateUser(u,uuId);
            return new ResponseEntity<>(user,HttpStatus.CREATED) ;
        }

    @DeleteMapping("delete/{uuId}/{id}")
    public ResponseEntity<User> deleteUser
            (@PathVariable ("uuId")  String uuId,@PathVariable ("id") Integer id)
            throws CurrentUserException, UserException {

            User user = this.userService.deleteUser(uuId,id);
        return new ResponseEntity<>(user,HttpStatus.OK) ;

    }

    @GetMapping("read/{uuId}")
    public ResponseEntity<User> readUser(@PathVariable ("uuId") String uuId) throws CurrentUserException {

            User user = this.userService.readUser(uuId);
            return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @PostMapping("login")
    public ResponseEntity<CurrentUserSession>LogIn(@RequestBody LogIn LogIn) throws CurrentUserException {
        CurrentUserSession session=this.userService.LogIn(LogIn);
        return new ResponseEntity<>(session,HttpStatus.OK);
    }

    @DeleteMapping("logout/{uuId}")
    public ResponseEntity<String>LogOut(@PathVariable String uuId) throws CurrentUserException {
        String message=this.userService.LogOut(uuId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


    @GetMapping("Hello")
    public ResponseEntity<String>helloWorld(){
       try{
           String m="hello world";

        return new ResponseEntity<>(m,HttpStatus.OK);
    }catch ( Exception e){
           String m="Failed" +e.getMessage();
           return new ResponseEntity<>(m,HttpStatus.EXPECTATION_FAILED);
       }

    }
}
