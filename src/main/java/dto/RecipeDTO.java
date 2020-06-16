package dto;

import entities.Recipe;

public class RecipeDTO {
    
    private Long id;
    private String preparationTime;
    private String directions;

    public RecipeDTO() {}
    
    public RecipeDTO(String preparationTime, String directions) {
        this.preparationTime = preparationTime;
        this.directions = directions;
    }

    public RecipeDTO(Long id, String preparationTime, String directions) {
        this.id = id;
        this.preparationTime = preparationTime;
        this.directions = directions;
    }
    
    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.preparationTime = recipe.getPreparationTime();
        this.directions = recipe.getDirections();
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}