package jwt_updated_with_springbootsecurity.Exception;


public class CurrentUserException extends Exception {
    private static final long serialVersionUID = 1L;

    public CurrentUserException() {
    }

    public CurrentUserException(String m) {
        super(m);
    }

}
