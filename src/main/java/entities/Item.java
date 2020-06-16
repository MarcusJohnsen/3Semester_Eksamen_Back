package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Item.deleteAllRows", query = "DELETE from Item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //valgt at bruge en String pga møntfod, f.eks. $
    private String pricePerKilo;
    
    @ManyToOne
    private Storage storage;
    
    @OneToMany(mappedBy = "item")
    private List<Ingredient> ingredientList = new ArrayList();  

    public Item() {}
    
    public Item(String name, String pricePerKilo) {
        this.name = name;
        this.pricePerKilo = pricePerKilo;
    }
    
    public Item(String name, String pricePerKilo, Storage storage) {
        this.name = name;
        this.pricePerKilo = pricePerKilo;
        this.storage = storage;
    }
    
    public Item(Long id, String name, String pricePerKilo, Storage storage) {
        this.id = id;
        this.name = name;
        this.pricePerKilo = pricePerKilo;
        this.storage = storage;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPricePerKilo() {
        return pricePerKilo;
    }

    public void setPricePerKilo(String pricePerKilo) {
        this.pricePerKilo = pricePerKilo;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}