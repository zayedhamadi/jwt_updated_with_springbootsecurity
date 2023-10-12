package jwt_updated_with_springbootsecurity.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException,
            ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer=response.getWriter();

        writer.println("Access Denied "+ authException.getMessage());

    }

}
/*Son rôle est de gérer les cas où un utilisateur non authentifié tente d'accéder à une ressource protégée,
 mais échoue en raison d'une authentification incorrecte ou de l'absence d'authentification.*/