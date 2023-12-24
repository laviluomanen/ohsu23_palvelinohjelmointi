package eventRegister;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;


@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public String list(Model model) {
        model.addAttribute("events", this.eventRepository.findAll());
        return "/events";
    }

    @PostMapping("/events")
    public String create(@RequestParam String event_title, @RequestParam("event_description") String event_description, @RequestParam("event_date") String e_date, @RequestParam("event_time") String e_time,@RequestParam("due_date") String d_date) throws ParseException {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate event_date = LocalDate.parse(e_date, df1);
        LocalDate due_date = LocalDate.parse(d_date, df1);

        LocalTime event_time = LocalTime.parse(e_time, df2);

        this.eventRepository.save(new Events(event_title, event_description, event_date, event_time, due_date));
        return "redirect:/events";
    }

}
