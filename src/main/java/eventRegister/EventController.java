package eventRegister;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/events")
    public String list(Model model) {
        model.addAttribute("events", this.eventRepository.findAll());
        return "/events";
    }
    //Further checks should be done where due date cannot be before event date, etc.
    @PostMapping("/events")
    public String create(@RequestParam String event_title, @RequestParam("event_description") String event_description,
                         @RequestParam("event_date") String e_date, @RequestParam("event_time") String e_time,
                         @RequestParam("due_date") String d_date) throws ParseException {
        //For the date inputs
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //For the time inputs
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("HH:mm");
        //Parsing the dates and time with formatters
        LocalDate event_date = LocalDate.parse(e_date, df1);
        LocalDate due_date = LocalDate.parse(d_date, df1);
        LocalTime event_time = LocalTime.parse(e_time, df2);
        Events eventToSave = new Events(event_title, event_description, event_date, event_time, due_date, new ArrayList<>());
        this.eventRepository.save(eventToSave);
        return "redirect:/events";
    }

    @GetMapping("events/{id}")
    public String getOne(@PathVariable(value = "id") long id, Model model) {
        Events event = this.eventRepository.getOne(id);
        model.addAttribute("event", event);
        return "event";
    }

    @PostMapping("/events/delete")
    public String delete(@RequestParam long id){
        this.eventRepository.deleteById(id);
        return "redirect:/events";
    }

    @PostMapping("/events/update")
    public String update(@RequestParam long id, @RequestParam String event_title, @RequestParam("event_description") String event_description,
                         @RequestParam("event_date") String e_date, @RequestParam("event_time") String e_time,
                         @RequestParam("due_date") String d_date) throws ParseException {
        Events eventToUpdate = this.eventRepository.getOne(id);
        //For the date inputs
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //For the time inputs
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("HH:mm");
        //Parsing the dates and time with formatters


        //An ugly way to circumvent the possibility that empty parameters are passed
        //Model could not be passed as such as date and time Strings need to be formatted and
        //correct variables need to be initialized
        if(!event_title.isEmpty()){
            eventToUpdate.setEvent_title(event_title);
        } else{
            eventToUpdate.setEvent_title(eventToUpdate.getEvent_title());
        }
        if(!event_description.isEmpty()){
            eventToUpdate.setEvent_description(event_description);
        } else{
            eventToUpdate.setEvent_description(eventToUpdate.getEvent_description());
        }
        if(!e_date.isEmpty()) {
            LocalDate event_date = LocalDate.parse(e_date, df1);
            eventToUpdate.setEvent_date(event_date);
        } else{
            eventToUpdate.setEvent_date(eventToUpdate.getEvent_date());
        }
        if(!d_date.isEmpty()){
            LocalDate due_date = LocalDate.parse(d_date, df1);
            eventToUpdate.setDue_date(due_date);
        } else {
            eventToUpdate.setDue_date(eventToUpdate.getDue_date());
        }
        if(!e_time.isEmpty()){
            LocalTime event_time = LocalTime.parse(e_time, df2);
            eventToUpdate.setEvent_time(event_time);
        } else {
            eventToUpdate.setEvent_time(eventToUpdate.getEvent_time());
        }

        this.eventRepository.save(eventToUpdate);
        return "redirect:/events";
    }
    //This method adds event to a category
    @PostMapping("/events/addCategory")
    public String addCategory(@RequestParam String category, @RequestParam long id){
        List<Category> cat = this.categoryRepository.findAll();
        //Event ID has been passed from the form and that's being used
        Events event = this.eventRepository.getOne(id);
        //a variable to store the info whether a category exists already
        int categoryFound = 0;
        long index = 0;
        for(int i=0; i<cat.size(); i++){
            if(cat.get(i).getCategory_name().toLowerCase().equals(category.toLowerCase())){
                categoryFound++;
                index = cat.get(i).getId();
                break;
            }
        }
        //If the category exists, event is added to it and saved
        if(categoryFound == 1){
            Category ct = this.categoryRepository.getOne(index);
            event.getCategories().add(ct);
            this.eventRepository.save(event);
            //If the category does not exist yet, it's created and event saved
        } else {
            Category newCt = new Category(category, new ArrayList<>());
            this.categoryRepository.save(newCt);
            List<Category> tmpCtList = categoryRepository.findAll();
            event.getCategories().add(newCt);
            this.eventRepository.save(event);
        }
        return "redirect:/events";
    }

    public boolean doesEventExist(String proposedEventName){
        boolean exists = false;
        List<Events> events = this.eventRepository.findAll();
        for(int i=0; i<events.size(); i++){
            if(events.get(i).getEvent_title().toLowerCase().equals(proposedEventName.toLowerCase())){
                exists = true;
            }
        }
        return exists;
    }
}

