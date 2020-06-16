package facades;

import dto.RecipeDTO;
import entities.Recipe;
import entities.User;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

public class RecipeFacadeTest {

    private static EntityManagerFactory emf;
    private static RecipeFacade facade;
    private static Recipe rec1, rec2, rec3;
    private static Recipe[] recipeArray;
    private static User u1, u2;
    private static String p1, p2;

    public RecipeFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/Semester3Eksamen_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = RecipeFacade.getRecipeFacade(emf);
    }

    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = RecipeFacade.getRecipeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            p1 = "123456";
            p2 = "TopSecretPassword";
            u1 = new User("Mark", p1);
            u2 = new User("Jensen", p2);
            rec1 = new Recipe("spaghetti", "ca. 20 min", "Cook until al dente!");
            rec2 = new Recipe("cordon bleu", "ca. 25 min", "Put on the pan for 25 mins, that's it");
            rec3 = new Recipe("svickova", "2 hours", "Just leave the meat in the oven for 1 and a half hours and make the omacku");
            em.persist(u1);
            em.persist(u2);
            em.persist(rec1);
            em.persist(rec2);
            em.persist(rec3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        recipeArray = new Recipe[]{rec1, rec2, rec3};
        /*highestId = 0L;
        for (InternalJoke joke : jokes) {
            if (joke.getId() > highestId) {
                highestId = joke.getId();
            }
        } */
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testRecipeCount() {
        assertEquals(3, facade.getRecipeCount(), "Expects three rows in the database");
    }

    @Test
    public void testAddRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO("Butter chicken", "1hr 15mins", "Chicken first, then cook rice while doing sauce.");

        RecipeDTO result = facade.addRecipe(recipeDTO);

        assertTrue(result.getRecipeName().equals(recipeDTO.getRecipeName()));
        assertTrue(result.getPreparationTime().equals(recipeDTO.getPreparationTime()));
        assertTrue(result.getDirections().equals(recipeDTO.getDirections()));
    }
}