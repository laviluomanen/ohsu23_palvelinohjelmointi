package eventRegister;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users extends AbstractPersistable<Long> {

    @Column(name="username", unique=true)
    private String username;
    @Column (name = "password_hash")
    private String password;

}
