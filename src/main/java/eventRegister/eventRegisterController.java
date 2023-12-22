package eventRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class eventRegisterController {

    @Autowired
    private eventRepository eventRepository;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("fishermen", this.eventRepository.findAll());
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String name) {
        this.eventRepository.save(new Users(name));
        return "redirect:/";
    }

}
