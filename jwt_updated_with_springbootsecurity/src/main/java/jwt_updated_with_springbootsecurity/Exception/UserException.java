package jwt_updated_with_springbootsecurity.Exception;

public class UserException  extends  Exception{
    private static final long serialVersionUID = 1L;

    public UserException() {
    }

    public UserException(String m) {
        super(m);
    }

}
