package jwt_updated_with_springbootsecurity.Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.*;
import java.util.*;
import io.jsonwebtoken.*;

@Component
public class    JwtHelp {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public String ToDecode = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public Date ExpirationDate=new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
    public Date StartDate=new Date(System.currentTimeMillis());



    //get Username from Token
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    //get Expiration Date from Token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //Get Claims that i need such as date or name or passwod (customs )from Token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Key GetSigninKey() {
        byte[] key = Decoders.BASE64.decode(ToDecode);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(GetSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return generateToken(claims, userDetails.getUsername());

    }

    private String generateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(StartDate)
                .setExpiration(ExpirationDate)
                .signWith(GetSigninKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}
