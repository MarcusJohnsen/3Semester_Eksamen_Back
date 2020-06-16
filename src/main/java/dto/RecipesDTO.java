package dto;

import entities.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipesDTO {
    
    private List<RecipeDTO> recipeList = new ArrayList();
    
    public RecipesDTO(){
    }

    public RecipesDTO(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            this.recipeList.add(new RecipeDTO(recipe));
        }
    }

    public List<RecipeDTO> getRecipes() {
        return recipeList;
    }

    public void setRecipes(List<RecipeDTO> RecipesList) {
        this.recipeList = recipeList;
    }
}