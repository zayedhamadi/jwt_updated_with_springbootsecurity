package jwt_updated_with_springbootsecurity.Repository;

import jwt_updated_with_springbootsecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

     Optional<User>findByEmail(String email);
}
