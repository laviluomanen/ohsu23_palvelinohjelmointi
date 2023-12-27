package eventRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String create(@RequestParam String username) {
        //@RequestParam String password_hash , password_hash
        this.userRepository.save(new Users(username));
        return "redirect:/users";
    }

    @GetMapping("users/{id}")
    public String getOne(@PathVariable(value = "id") long id, Model model) {
        Users user = this.userRepository.getOne(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/users/delete")
    public String delete(@RequestParam long id){
        this.userRepository.deleteById(id);
        return "redirect:/users";
    }

    @PostMapping("users/update")
    public String update(@RequestParam long id, @RequestParam String username){
        Users userToUpdate = this.userRepository.getOne(id);

        if(!username.isEmpty()){
            userToUpdate.setUsername(username);
        } else{
            userToUpdate.setUsername(userToUpdate.getUsername());
        }
        this.userRepository.save(userToUpdate);
        return "redirect:/users";
    }

}
