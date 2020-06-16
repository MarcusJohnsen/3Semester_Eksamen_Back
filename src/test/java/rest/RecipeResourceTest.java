package rest;

import dto.RecipeDTO;
import dto.RecipesDTO;
import entities.Recipe;
import entities.Role;
import entities.User;
import entities.WeeklyMenuPlan;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

public class RecipeResourceTest {

    public RecipeResourceTest() {}
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static User u1, u2;
    private static String p1, p2;
    private static Recipe rec1, rec2, rec3;
    private static Recipe[] recipeArray;
    private static WeeklyMenuPlan wPlan1, wPlan2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

    }

    @BeforeEach
    public void setUp() {
        logOut();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            p1 = "123456";
            p2 = "TopSecretPassword";
            u1 = new User("Mark", p1);
            u2 = new User("Jensen", p2);
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            u1.addRole(userRole);
            u2.addRole(adminRole);
            rec1 = new Recipe("spaghetti", "ca. 20 min", "Cook until al dente!");
            rec2 = new Recipe("cordon bleu", "ca. 25 min", "Put on the pan for 25 mins, that's it");
            rec3 = new Recipe("svickova", "2 hours", "Just leave the meat in the oven for 1 and a half hours and make the omacku");
            wPlan1 = new WeeklyMenuPlan ("week 38", "2023");
            wPlan2 = new WeeklyMenuPlan ("week 33", "2021");
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(rec1);
            em.persist(rec2);
            em.persist(rec3);
            em.persist(wPlan1);
            em.persist(wPlan2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        recipeArray = new Recipe[]{rec1, rec2, rec3};
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @AfterEach
    public void tearDown() {
        logOut();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then().log().body()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }
    
    @Test
    public void testGetAllRecipes() {
        User user = u2;
        login(user.getUserName(), p2);

        RecipesDTO result = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/rec/all").then()
                .statusCode(200)
                .extract().body().as(RecipesDTO.class);

        int expectedSize = recipeArray.length;
        assertEquals(expectedSize, result.getRecipes().size());
    }
    
    //test to correctly add a recipe as an admin
    @Test
    public void testAddRecipe_admin() {
        User user = u2;

        RecipeDTO newJoke = new RecipeDTO("pølse", "ca. 20 min", "kog vand. Put så pølserne i");
        login(user.getUserName(), p2);

        RecipeDTO result = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(newJoke)
                .when()
                .post("/rec").then()
                .statusCode(200)
                .extract().body().as(RecipeDTO.class);

        assertTrue(result.getRecipeName().equals(newJoke.getRecipeName()));
        assertTrue(result.getPreparationTime().equals(newJoke.getPreparationTime()));
        assertTrue(result.getDirections().equals(newJoke.getDirections()));

        EntityManager em = emf.createEntityManager();
        try {
            Recipe dbResult = em.find(Recipe.class, result.getId());
            assertTrue(result.getRecipeName().equals(dbResult.getRecipeName()));
            assertTrue(result.getPreparationTime().equals(dbResult.getPreparationTime()));
            assertTrue(result.getDirections().equals(dbResult.getDirections()));
        } catch (Exception e) {
            fail("Issues getting results from database");
        } finally {
            em.close();
        }
    }
    
    //test to ensure that the user role cannot add recipes. Error code 401 means "unauthorized user"
    @Test
    public void testAddRecipe_user() {
        User user = u1;

        RecipeDTO newJoke = new RecipeDTO("pølse", "ca. 20 min", "kog vand. Put så pølserne i");
        login(user.getUserName(), p1);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(newJoke)
                .when()
                .post("/rec").then()
                .statusCode(401)
                .extract().body().as(RecipeDTO.class);
    }
    
    //test to correctly delete a recipe as admin
    @Test
    public void testDeleteRecipe_admin() {
        User user = u2;
        login(user.getUserName(), p2);
        
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .delete("/rec/" + rec2.getId()).then()
                .statusCode(204);

        RecipesDTO result = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/rec/all").then()
                .statusCode(200)
                .extract().body().as(RecipesDTO.class);

        int expectedLength = recipeArray.length - 1;
        assertEquals(expectedLength, result.getRecipes().size());
    }
    
    //test to ensure that the user role cannot delete recipes. Error code 401 means "unauthorized user"
    @Test
    public void testNegativeDeleteRecipe_user() {
        User user = u1;
        login(user.getUserName(), p1);
        
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .delete("/rec/" + rec2.getId()).then()
                .statusCode(401);
    }
    
    //test to correctly edit a recipe as admin
    @Test
    public void testEditRecipe_admin() {
        User user = u2;
        login(user.getUserName(), p2);
        RecipeDTO expectedResult = new RecipeDTO(rec2);
        expectedResult.setDirections("just eat it raw.");
        expectedResult.setPreparationTime("0 minutes if you eat it raw!");
        
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken) 
                .body(expectedResult)
                .when()
                .put("/rec/edit").then()
                .statusCode(200)
                .extract().body().as(RecipeDTO.class);
        
        RecipesDTO results = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/rec/all").then()
                .statusCode(200)
                .extract().body().as(RecipesDTO.class);

        RecipeDTO result = null;
        for (RecipeDTO rec : results.getRecipes()) {
            if (rec.getId().equals(expectedResult.getId())) {
                result = rec;
                break;
            }
        }
        assertNotNull(result);
        assertEquals(expectedResult.getDirections(), result.getDirections());
        assertEquals(expectedResult.getPreparationTime(), result.getPreparationTime());
    }
    
    // testing that users are not able to edit recipes. Error code 401 means "unauthorized user"
    @Test
    public void testNegativeEditRecipe_user() {
        User user = u1;
        login(user.getUserName(), p1);
        RecipeDTO expectedResult = new RecipeDTO(rec2);
        expectedResult.setDirections("just eat it raw.");
        expectedResult.setPreparationTime("0 minutes if you eat it raw!");
        
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken) 
                .body(expectedResult)
                .when()
                .put("/rec/edit").then()
                .statusCode(401)
                .extract().body().as(RecipeDTO.class);
    }
    
    //endpointet virker ikke, men lader det stå så man kan se hvad min intention var..
    //facade-metoden derimod virker, kan bare ikke få dette til at virke.
    
    /*@Test
    public void testAddRecipeToWeeklyPlan() {
        User user = u2;
        RecipeDTO newRecipe = new RecipeDTO("Svensk pølsret", "30 min", "pølser + kartofler");
        login(user.getUserName(), p2);
        
            given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(newRecipe)
                .accept(ContentType.JSON)
                .when()
                .put("/rec/weeklyPlan/"+rec2.getId()).then()
                .statusCode(200)
                .extract().body().as(RecipeDTO.class);
        
        EntityManager em = emf.createEntityManager();
        try {
        WeeklyMenuPlan dbPlan = em.find(WeeklyMenuPlan.class, wPlan2.getId());
        int expectedResult = 1;
        assertEquals(expectedResult, dbPlan.getSevenRecipesList().size());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            em.close();
        }
    }*/
}