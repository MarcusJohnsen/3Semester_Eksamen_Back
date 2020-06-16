package facades;

import dto.RecipeDTO;
import dto.RecipesDTO;
import entities.Recipe;
import entities.WeeklyMenuPlan;
import errorhandling.InvalidInputException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class RecipeFacade {

    private static RecipeFacade instance;
    private static EntityManagerFactory emf;
    
    private RecipeFacade() {
    }
    
    public static RecipeFacade getRecipeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RecipeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public long getRecipeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long recipeCount = (long) em.createQuery("SELECT COUNT(r) FROM Recipe r").getSingleResult();
            return recipeCount;
        } finally {
            em.close();
        }
    }
    
    public RecipesDTO getRecipes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipe r", Recipe.class);
            List<Recipe> dbList = query.getResultList();
            RecipesDTO result = new RecipesDTO(dbList);
            return result;
        } finally {
            em.close();
        }
    }
    
    public RecipeDTO addRecipe(RecipeDTO recipe) {
        EntityManager em = emf.createEntityManager();
        try {
            Recipe rec = new Recipe(recipe.getRecipeName(), recipe.getPreparationTime(), recipe.getDirections());
            em.getTransaction().begin();
            em.persist(rec);
            em.getTransaction().commit();
            RecipeDTO newRecipe = new RecipeDTO(rec);
            return newRecipe;
        } finally {
            em.close();
        }
    }
    
    public RecipeDTO editRecipe(RecipeDTO recipe) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Recipe rec = em.find(Recipe.class, recipe.getId());
            rec.setRecipeName(recipe.getRecipeName());
            rec.setPreparationTime(recipe.getPreparationTime());
            rec.setDirections(recipe.getDirections());
            //rec.setIngredientList(recipe.);
            em.getTransaction().commit();
            RecipeDTO result = new RecipeDTO(rec);
            return result;
        }finally{  
            em.close();
        }
    }
    
    public void deleteRecipe(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Recipe rec = em.find(Recipe.class, id);
            em.getTransaction().begin();
            em.remove(rec);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    //mest for at vise at jeg kan lave errorhandling. Laver det flere steder hvis jeg har tiden
    public RecipeDTO addRecipeToWeeklyMenuPlan(Long id, Long id2) throws InvalidInputException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            WeeklyMenuPlan week28 = em.find(WeeklyMenuPlan.class, id);
            Recipe chosenRec = em.find(Recipe.class, id2);
            if(!week28.getSevenRecipesList().contains(chosenRec)){
            week28.getSevenRecipesList().add(chosenRec);    
            }
            em.getTransaction().commit();
            RecipeDTO newRecipe = new RecipeDTO(chosenRec);
            return newRecipe;
        } catch (NullPointerException e) {
            throw new InvalidInputException("This recipe does not exist. Might wanna create it again first!");
        } finally {
            em.close();
        }
    }
    
    public RecipeDTO searchRecipeByID(Long id) {
        EntityManager em = emf.createEntityManager();
        try{
            Recipe query = em.createQuery("SELECT r FROM Recipe r WHERE r.id = :id", Recipe.class)
                .setParameter("id", id) 
                .getSingleResult();
            RecipeDTO result = new RecipeDTO(query);
            return result;
        } finally {
            em.close();
        }
    }
    
    public RecipeDTO searchRecipeByName(String name) {
        EntityManager em = emf.createEntityManager();
        try{
            Recipe query = em.createQuery("SELECT r FROM Recipe r WHERE r.recipeName = :name", Recipe.class)
                .setParameter("name", name)    
                .getSingleResult();
            RecipeDTO result = new RecipeDTO(query);
            return result;
        } finally {
            em.close();
        }
    }
}