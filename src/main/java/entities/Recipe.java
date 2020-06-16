package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Recipe.deleteAllRows", query = "DELETE from Recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /*In the task description there's no option to give recipes a recipeName.
    To me that makes sense to add */
    private String recipeName;
    //String so you can write it like "10 min prep time"
    private String preparationTime;
    private String directions;
    
    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredientList = new ArrayList();  
    
    @ManyToMany
    private List<WeeklyMenuPlan> weeklyPlanList = new ArrayList();

    public Recipe() {}
    
    public Recipe(String recipeName, String preparationTime, String directions) {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.directions = directions;
    }

    public Recipe(Long id, String recipeName, String preparationTime, String directions) {
        this.id = id;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.directions = directions;
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

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<WeeklyMenuPlan> getWeeklyPlanList() {
        return weeklyPlanList;
    }

    public void setWeeklyPlanList(List<WeeklyMenuPlan> weeklyPlanList) {
        this.weeklyPlanList = weeklyPlanList;
    }
}