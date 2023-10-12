package jwt_updated_with_springbootsecurity.Service;

import jwt_updated_with_springbootsecurity.Entity.CurrentUserSession;
import jwt_updated_with_springbootsecurity.Entity.LogIn;
import jwt_updated_with_springbootsecurity.Entity.User;
import jwt_updated_with_springbootsecurity.Exception.CurrentUserException;
import jwt_updated_with_springbootsecurity.Exception.UserException;

public interface UserService {


    String addUser() throws UserException;

    User updateUser(User u, String uuId) throws CurrentUserException;

    User deleteUser(String uuId,Integer id)throws UserException,CurrentUserException;

    User readUser(String uuId) throws CurrentUserException;

    CurrentUserSession LogIn(LogIn LogIn) throws CurrentUserException;

    String LogOut(String uuId) throws CurrentUserException;


}
