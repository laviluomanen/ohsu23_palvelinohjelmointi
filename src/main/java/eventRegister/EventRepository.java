package eventRegister;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Events, Long> {
}
