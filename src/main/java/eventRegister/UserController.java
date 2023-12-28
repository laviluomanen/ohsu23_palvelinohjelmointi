package eventRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", this.userRepository.findAll());
        return "/users";
    }

    @PostMapping("/users")
    public String create(@RequestParam String username, @RequestParam String password) {
        if(password.isEmpty()){
            password = "12345";
            System.out.println("User created with default password, change it.");
        }
        if(!username.isEmpty()){
            boolean exists = doesUserExist(username);
            if(exists == false){
                Users newUser = new Users(username, passwordEncoder.encode(password));
                this.userRepository.save(newUser);
                System.out.println("User created successfully.");
            }
            else{
                System.out.println("Username exists already.");
            }

        }
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
        System.out.println("User deleted successfully.");
        return "redirect:/users";
    }



    @PostMapping("users/update")
    public String update(@RequestParam long id, @RequestParam String username, String password){
        Users userToUpdate = this.userRepository.getOne(id);
        //Pretty lazy and not the best practice.
        if(!username.isEmpty()){
            boolean exists = doesUserExist(username);
            if(exists == false){
                userToUpdate.setUsername(username);
            }else {
                //It'd be better to return an informative page at this point
                System.out.println("User exists already!");
            }
        } else{
            userToUpdate.setUsername(userToUpdate.getUsername());

        }
        if(!password.isEmpty()){
            userToUpdate.setPassword(passwordEncoder.encode(password));
        } else {
            userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        }
        return "redirect:/users";
    }

    //Needed by create and update methods to dismiss conflicts with unique username restriction
    public boolean doesUserExist(String proposedUsername){
        boolean exists = false;
        List<Users> users = this.userRepository.findAll();
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getUsername().toLowerCase().equals(proposedUsername.toLowerCase())){
                exists = true;
            }
        }
        return exists;
    }

}
