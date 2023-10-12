package jwt_updated_with_springbootsecurity.Service;


import jwt_updated_with_springbootsecurity.Entity.CurrentUserSession;
import jwt_updated_with_springbootsecurity.Entity.LogIn;
import jwt_updated_with_springbootsecurity.Entity.User;
import jwt_updated_with_springbootsecurity.Exception.CurrentUserException;
import jwt_updated_with_springbootsecurity.Exception.UserException;
import jwt_updated_with_springbootsecurity.Repository.SessionRepository;
import jwt_updated_with_springbootsecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public String  addUser() throws UserException {
//        if (u.getRole().equals("ADMIN") || u.getRole().equals("USER")) {
//
//            User user = this.userRepository.save(u);
//            return user;
//        } else {
//            throw new UserException("Enter correct Role");
//        }
        User u=new User(1,"zayed","zayedhamadi1919@gmail.com","123456789","ADMIN");
        User u1=new User(2,"zayed","zayedhamadi1919@gmail.com","123456789","USER");
        User u2=userRepository.save(u);
        User u3=userRepository.save(u1);
        return u2.toString() +" "+u3.toString();

    }

    @Override
    public User updateUser(User u, String uuId) throws CurrentUserException {
        // Retrieve the user with the given UUID
        User loggedInUser = this.getUser(uuId);

        if (loggedInUser != null && loggedInUser.getRole() != null) {
            // Check if the logged-in user has the role "ADMIN"
            if (loggedInUser.getRole().equals("ADMIN") || loggedInUser.getRole().equals("User")) {
                // Check if the ID of the logged-in user matches the ID of the user being updated
                if (loggedInUser.getId().equals(u.getId())) {
                    // Save the updated user
                    User updatedUser = this.userRepository.save(u);
                    return updatedUser;
                } else {
                    throw new CurrentUserException("User with provided UUID does not exist or has no role assigned");
                }
            } else {
                throw new CurrentUserException("You don't have access to update this user");
            }
        } else {
            throw new CurrentUserException("You don't have the required role to perform this action");
        }
    }


    @Override
    public User deleteUser(String uuId, Integer id) throws CurrentUserException, UserException {
        // Retrieve the user with the given UUID
        User loggedInUser = this.readUser(uuId);

        if (loggedInUser != null) {
            // Check if the logged-in user has the role "ADMIN"
            if (loggedInUser.getRole().equals("ADMIN")) {
                // Find the user to be deleted by their ID
                Optional<User> optionalUser = userRepository.findById(id);
                if (optionalUser.isPresent()) {
                    User userToDelete = optionalUser.get();

                    // Find the session by email and log out if found
                    Optional<CurrentUserSession> opsession = sessionRepository.findById(userToDelete.getEmail());
                    if (opsession.isPresent()) {
                        this.LogOut(opsession.get().getUuId());
                    }

                    // Delete the user and return the deleted user
                    userRepository.delete(userToDelete);
                    return userToDelete;
                } else {
                    throw new UserException("User with provided ID not found");
                }
            } else {
                throw new CurrentUserException("You don't have the required role (ADMIN) to perform this action");
            }
        } else {
            throw new CurrentUserException("User with provided UUID does not exist");
        }
    }

// Other methods remain the same


    @Override
    public User readUser(String uuId) throws CurrentUserException {

        User u = this.getUser(uuId);
        if (u != null) {
            return u;
        } else {
            throw new CurrentUserException("User Id is not present ");
        }
    }

    @Override
    public CurrentUserSession LogIn(LogIn LogIn) throws CurrentUserException {
        Optional<User> optionaluser = userRepository.findByEmail(LogIn.getEmail());
        if (optionaluser.isPresent()) {
            User user = optionaluser.get();
            if (user.getPassword().equals(LogIn.getPassword())) {
                Optional<CurrentUserSession> optionalCurrentUserSession = sessionRepository.findById(LogIn.getEmail());
                if (optionalCurrentUserSession.isEmpty()) {
                    String key = this.randomSting();
                    CurrentUserSession session = new CurrentUserSession(LogIn.getEmail(), user.getId(), key);

                    return sessionRepository.save(session);
                } else {
                    throw new CurrentUserException(" User already present");
                }
            } else {
                throw new CurrentUserException(" Password not Found or Wrong ");
            }
        } else {
            throw new CurrentUserException(" Email not Found or Wrong ");
        }
    }

    @Override
    public String LogOut(String uuId) throws CurrentUserException {
        Optional<CurrentUserSession> optionalCurrentUserSession = sessionRepository.findByUuId(uuId);
        if (optionalCurrentUserSession.isPresent()) {
            CurrentUserSession session = optionalCurrentUserSession.get();
            sessionRepository.delete(session);
            return "LogOut " + session.getEmail();
        } else {
            throw new CurrentUserException("wrong unique user Id :");
        }
    }

    private String randomSting() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789@#$%&";
        int length = 6;
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            str.append(alphabet.charAt(index));
        }
        return str.toString();
    }

    private User getUser(String uuId) {
        Optional<CurrentUserSession> op = this.sessionRepository.findByUuId(uuId);
        if (op.isPresent()) {
            CurrentUserSession session = op.get();
            Optional<User> opUser = this.userRepository.findById(session.getUserId());
            return opUser.get();
        }
        return null;
    }
}
