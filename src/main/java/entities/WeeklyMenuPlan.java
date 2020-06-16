package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class WeeklyMenuPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //user able to write "week 33"
    private String week;
    private String year;
    
    @ManyToMany(mappedBy = "weeklyPlanList")
    private List<Recipe> sevenRecipesList = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Recipe> getSevenRecipesList() {
        return sevenRecipesList;
    }

    public void setSevenRecipesList(List<Recipe> sevenRecipesList) {
        this.sevenRecipesList = sevenRecipesList;
    }
}