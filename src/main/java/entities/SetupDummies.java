package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class SetupDummies {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/Semester3Eksamen",
            "dev",
            "ax2",
            EMF_Creator.Strategy.DROP_AND_CREATE);

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Storage.deleteAllRows").executeUpdate();
            em.createNamedQuery("Item.deleteAllRows").executeUpdate();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createNamedQuery("Ingredient.deleteAllRows").executeUpdate();
            em.createNamedQuery("WeeklyMenuPlan.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
          addEntities();
    }

    private static void addEntities() {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Storage storage1 = new Storage(1000);
            Storage storage2 = new Storage(800);
            Storage storage3 = new Storage(1827);
        
            Item item1 = new Item("rice", "8 DKK", storage1);
            Item item2 = new Item("chicken", "100 DKK", storage2);
            Item item3 = new Item("tomato", "20 DKK", storage3);
            
            Recipe recipe1 = new Recipe("Butter Chicken", "1hr", "lots of curry and chili.");
            Recipe recipe2 = new Recipe("Chinese", "0.5hr", "fried rice is life");
            Recipe recipe3 = new Recipe("Mexican", "3hr", "Quesadillas is life");
            Recipe recipe4 = new Recipe("Nigerian", "2hr", "Jolof rice is life");
            Recipe recipe5 = new Recipe("Italian", "50min", "OG pizza");
            Recipe recipe6 = new Recipe("Czech", "1hr", "Svickova is life");
            Recipe recipe7 = new Recipe("Turkish", "45min", "Dolma is life");
            
            
            Ingredient ingredient1 = new Ingredient(20, item2, recipe4);
            Ingredient ingredient2 = new Ingredient(35, item1, recipe4);
            Ingredient ingredient3 = new Ingredient(52, item2, recipe1);
            Ingredient ingredient4 = new Ingredient(11, item3, recipe2);
            Ingredient ingredient5 = new Ingredient(100, item1, recipe5);
            
            WeeklyMenuPlan plan1 = new WeeklyMenuPlan("week 24", "year 2020");
            WeeklyMenuPlan plan2 = new WeeklyMenuPlan("week 25", "year 2020");
            WeeklyMenuPlan plan3 = new WeeklyMenuPlan("week 26", "year 2020");
            WeeklyMenuPlan plan4 = new WeeklyMenuPlan("week 27", "year 2020");
            WeeklyMenuPlan plan5 = new WeeklyMenuPlan("week 28", "year 2020");
            
            em.persist(storage1);
            em.persist(storage2);
            em.persist(storage3);
            
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            
            em.persist(recipe1);
            em.persist(recipe2);
            em.persist(recipe3);
            em.persist(recipe4);
            em.persist(recipe5);
            em.persist(recipe6);
            em.persist(recipe7);
            
            em.persist(ingredient1);
            em.persist(ingredient2);
            em.persist(ingredient3);
            em.persist(ingredient4);
            em.persist(ingredient5);
            
            em.persist(plan1);
            em.persist(plan2);
            em.persist(plan3);
            em.persist(plan4);
            em.persist(plan5);
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}