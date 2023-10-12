package jwt_updated_with_springbootsecurity.Entity;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogIn {

    private String email;
    private String password;

}
