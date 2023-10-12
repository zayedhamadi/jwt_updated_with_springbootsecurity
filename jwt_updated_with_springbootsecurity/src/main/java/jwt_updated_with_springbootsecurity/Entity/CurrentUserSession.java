package jwt_updated_with_springbootsecurity.Entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserSession {
    @Id

      private String email;

      private Integer userId;

      private String uuId;//unique Id User

}