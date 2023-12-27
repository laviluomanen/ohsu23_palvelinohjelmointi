package eventRegister;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);


    @Query("select u.id from Users u where u.username = ?1")
    Users findIDbyUsername(String username);

}
