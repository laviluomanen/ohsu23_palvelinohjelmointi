package eventRegister;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category extends AbstractPersistable<Long> {

    @Column(name = "category_name", unique = true)
    private String category_name;
    @ManyToMany(mappedBy = "categories")
    @Column(name = "events")
    private List<Events> events;
}
