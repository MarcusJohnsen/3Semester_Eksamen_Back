package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Ingredient.deleteAllRows", query = "DELETE from Ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    
    @ManyToOne
    private Item item;
    @ManyToOne
    private Recipe recipe;

    public Ingredient(){}
    
    public Ingredient(int amount) {
        this.amount = amount;
        this.item = item;
    }
    
    public Ingredient(Long id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Ingredient(int amount, Item item, Recipe recipe) {
        this.amount = amount;
        this.item = item;
        this.recipe = recipe;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}