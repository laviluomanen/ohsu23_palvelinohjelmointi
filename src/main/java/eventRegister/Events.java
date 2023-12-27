package eventRegister;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Events extends AbstractPersistable<Long> {
    @Column(name = "event_title")
    private String event_title;

    @Column(name = "event_description")
    private String event_description;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "event_date")
    private LocalDate event_date;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "event_time")
    private LocalTime event_time;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "due_date")
    private LocalDate due_date;

    @ManyToMany
    @JoinTable(
            name = "event_categories",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

}
