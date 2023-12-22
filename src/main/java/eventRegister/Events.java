package eventRegister;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Events extends AbstractPersistable<Long> {
    private String category_name;
    private String event_description;
    private Date event_date;
    //private Time event_time;
    private Date due_date;

}
