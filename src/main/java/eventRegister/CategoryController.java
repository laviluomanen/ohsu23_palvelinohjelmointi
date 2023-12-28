package eventRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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
        boolean exists = doesCategoryExist(category_name);
        if(exists == false){
            this.categoryRepository.save(new Category(category_name, new ArrayList<>()));
        } else {
            //It'd be better to return an informative page at this point
            System.out.println("Category exists already!");
        }

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
        List<Category> categories = this.categoryRepository.findAll();
        Category cat = this.categoryRepository.getOne(id);
        int eventQty = cat.getEvents().size();
        if(eventQty == 0){
            this.categoryRepository.deleteById(id);
        }
        else{
            System.out.println("Cannot delete category as " + eventQty + " events belong to the category.");
        }
        return "redirect:/categories";
    }

    @PostMapping("/categories/update")
    public String update(@RequestParam long id, @RequestParam String category_name) {
        Category categoryToUpdate = this.categoryRepository.getOne(id);

        //Pretty lazy and not the best practice.
        if(!category_name.isEmpty()){
            boolean exists = doesCategoryExist(category_name);
            if(exists == false){
                categoryToUpdate.setCategory_name(category_name);
            } else {
                //It'd be better to return an informative page at this point
                System.out.println("Category exists already!");
            }
        } else{
            //Update commit takes place but name changes the same, a workaround atm.
            categoryToUpdate.setCategory_name(categoryToUpdate.getCategory_name());
        }
        this.categoryRepository.save(categoryToUpdate);
        return "redirect:/categories";
    }

    //Needed by create and update methods to dismiss conflicts with unique category name restriction
    public boolean doesCategoryExist(String proposedCategoryName){
        boolean exists = false;
        List<Category> categories = this.categoryRepository.findAll();
        for(int i=0; i<categories.size(); i++){
            if(categories.get(i).getCategory_name().toLowerCase().equals(proposedCategoryName.toLowerCase())){
                exists = true;
            }
        }
        return exists;
    }
}
