package com.example.recipes.Controller;

import com.example.recipes.Model.Recipe;
import com.example.recipes.Repository.RecipeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository repository;

    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Recipe create(@RequestBody Recipe recipe){
        return this.repository.save(recipe);
    }

    @GetMapping("/{id}")
    public Recipe read(@PathVariable Long id) throws Exception{
        Optional<Recipe> optional = this.repository.findById(id);
        try{
            return optional.get();
        }
        catch(Exception e){
            throw new Exception("Id not found");
        }
    }

    @PatchMapping("/{id}")
    public Recipe update(@PathVariable Long id, @RequestBody Recipe recipe) throws Exception{
        Optional<Recipe> optional = this.repository.findById(id);
        try{
            if(recipe.getTitle() != null) optional.get().setTitle(recipe.getTitle());
            if(recipe.getDescription() != null) optional.get().setDescription(recipe.getDescription());
            if(recipe.getCalories() != null) optional.get().setCalories(recipe.getCalories());
            if(recipe.getFavorite() != null) optional.get().setFavorite(recipe.getFavorite());
            return this.repository.save(optional.get());
        }
        catch (Exception e){
            throw  new Exception("Id not found");
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws Exception{
        try{
            this.repository.deleteById(id);
            return "Deleted";
        }
        catch (Exception e){
            throw new Exception("Id not found");
        }
    }

    @GetMapping
    public Iterable<Recipe> list(){
        return this.repository.findAll();
    }
}
