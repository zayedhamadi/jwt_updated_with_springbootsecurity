package jwt_updated_with_springbootsecurity.Entity;


import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    private String email;
    private String password;

}