package eventRegister;


import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", this.categoryRepository.findAll());
        return "/categories";
    }

    @PostMapping("/categories")
    public String create(@RequestParam String category_name) {
        this.categoryRepository.save(new Categories(category_name));
        return "redirect:/categories";
    }

    @GetMapping("categories/{id}")
    public String getOne(@PathVariable(value = "id") long id, Model model) {
        Categories category = this.categoryRepository.getOne(id);
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
        Categories categoryToUpdate = this.categoryRepository.getOne(id);

        if(!category_name.isEmpty()){
            categoryToUpdate.setCategory_name(category_name);
        } else{
            categoryToUpdate.setCategory_name(categoryToUpdate.getCategory_name());
        }

        this.categoryRepository.save(categoryToUpdate);
        return "redirect:/categories";
    }
}
