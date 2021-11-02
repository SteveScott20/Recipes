package com.example.recipes;

import com.example.recipes.Model.Recipe;
import com.example.recipes.Repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    RecipeRepository repository;

    Recipe recipe = new Recipe();

    @BeforeEach
    public void initRecipe(){
        recipe.setTitle("Tacos");
        recipe.setDescription("Shell filled with stuff");
        recipe.setCalories(200);
        recipe.setFavorite(false);
    }

    @Transactional
    @Rollback
    @Test
    public void testCreate() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = post("/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.recipe));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Tacos")));
    }

    @Transactional
    @Rollback
    @Test
    public void testRead() throws Exception{
        Recipe test = this.repository.save(this.recipe);

        RequestBuilder request = get("/recipe/"+test.getId());

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Tacos")));
    }

    @Transactional
    @Rollback
    @Test
    public void testUpdate() throws Exception{
        Recipe recipe2 = new Recipe();
        recipe2.setCalories(400);

        ObjectMapper objectMapper = new ObjectMapper();

        Recipe test = this.repository.save(this.recipe);

        RequestBuilder request = patch("/recipe/"+test.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe2));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Tacos")))
                .andExpect(jsonPath("$.calories", equalTo(400)));
    }

    @Transactional
    @Rollback
    @Test
    public void testDelete() throws Exception{
        Recipe test = this.repository.save(this.recipe);

        RequestBuilder request = delete("/recipe/"+test.getId());

        MvcResult result = this.mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Deleted", result.getResponse().getContentAsString());
    }

    @Transactional
    @Rollback
    @Test
    public void testList() throws Exception{
        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Steak");

        this.repository.save(this.recipe);
        this.repository.save(recipe2);

        RequestBuilder request = get("/recipe");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", equalTo("Tacos")))
                .andExpect(jsonPath("$[1].title", equalTo("Steak")));
    }
}
