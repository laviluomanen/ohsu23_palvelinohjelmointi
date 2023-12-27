package eventRegister;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", this.categoryRepository.findAll());
        return "/categories";
    }

    @PostMapping("/categories")
    public String create(@RequestParam String category_name) {

        this.categoryRepository.save(new Category(category_name, new ArrayList<>()));
        return "redirect:/categories";
    }

    @GetMapping("categories/{id}")
    public String getOne(@PathVariable(value = "id") long id, Model model) {
        Category category = this.categoryRepository.getOne(id);
        model.addAttribute("category", category);
        return "category";
    }

    @PostMapping("/categories/delete")
    public String delete(@RequestParam long id){
        this.categoryRepository.deleteById(id);
        return "redirect:/categories";
    }

    @PostMapping("/categories/update")
    public String update(@RequestParam long id, @RequestParam String category_name) {
        Category categoryToUpdate = this.categoryRepository.getOne(id);

        //Pretty lazy and not the best practice.
        if(!category_name.isEmpty()){
            categoryToUpdate.setCategory_name(category_name);
        } else{
            categoryToUpdate.setCategory_name(categoryToUpdate.getCategory_name());
        }

        this.categoryRepository.save(categoryToUpdate);
        return "redirect:/categories";
    }
}
