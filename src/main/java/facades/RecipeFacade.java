package facades;

import dto.RecipeDTO;
import dto.RecipesDTO;
import entities.Recipe;
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
}