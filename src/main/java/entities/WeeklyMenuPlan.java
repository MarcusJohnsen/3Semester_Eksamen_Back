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

@Entity
@NamedQuery(name = "WeeklyMenuPlan.deleteAllRows", query = "DELETE from WeeklyMenuPlan")
public class WeeklyMenuPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //user able to write "week 33"
    private String weekNo;
    private String year;
    
    @ManyToMany(mappedBy = "weeklyPlanList")
    private List<Recipe> sevenRecipesList = new ArrayList();

    public WeeklyMenuPlan() {}

    public WeeklyMenuPlan(String weekNo, String year) {
        this.weekNo = weekNo;
        this.year = year;
    }
    
    public WeeklyMenuPlan(String weekNo, String year, ArrayList<Recipe> sevenRecipesList) {
        this.sevenRecipesList = sevenRecipesList;
        this.weekNo = weekNo;
        this.year = year;
    }
    
    public WeeklyMenuPlan(Long id, String weekNo, String year) {
        this.id = id;
        this.weekNo = weekNo;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
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