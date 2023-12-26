package eventRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", this.userRepository.findAll());
        return "/users";
    }

    @PostMapping("/users")
    public String create(@RequestParam String username, @RequestParam String password_hash) {
        this.userRepository.save(new Users(username, password_hash));
        return "redirect:/users";
    }

}
