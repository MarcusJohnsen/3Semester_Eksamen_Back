package dto;

import entities.Ingredient;

public class IngredientDTO {
    
    private String value;
    private String id;

    public IngredientDTO() {
    }

    public IngredientDTO(String value, String id) {
        this.value = value;
        this.id = id;
    }
    
    public IngredientDTO(Ingredient ingredient) {
        this.value = value;
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }
}