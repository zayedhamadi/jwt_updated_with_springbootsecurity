package jwt_updated_with_springbootsecurity.Repository;

import jwt_updated_with_springbootsecurity.Entity.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface SessionRepository extends JpaRepository<CurrentUserSession, String> {
        Optional<CurrentUserSession> findByUuId(String uuId);
}
