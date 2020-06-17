package dto;

import entities.Recipe;
import java.util.List;

public class RecipeDTO {
    
    private Long id;
    private String recipeName;
    private String preparationTime;
    private String directions;
    List<IngredientDTO> ingredients;

    public RecipeDTO() {}
    
    public RecipeDTO(String recipeName, String preparationTime, String directions) {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.directions = directions;
    }

    public RecipeDTO(Long id, String recipeName, String preparationTime, String directions) {
        this.id = id;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.directions = directions;
    }
    
    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.recipeName = recipe.getRecipeName();
        this.preparationTime = recipe.getPreparationTime();
        this.directions = recipe.getDirections();
        this.ingredients = recipe.getIngredientsDTO();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}