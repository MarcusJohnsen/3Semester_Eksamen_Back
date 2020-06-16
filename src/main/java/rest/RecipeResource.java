package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RecipeDTO;
import dto.RecipesDTO;
import utils.EMF_Creator;
import facades.RecipeFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("rec")
public class RecipeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/Semester3Eksamen",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final RecipeFacade FACADE =  RecipeFacade.getRecipeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getRecipeCount() {
        long count = FACADE.getRecipeCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addRecipe(String recipe) {
        RecipeDTO recipeAdd = GSON.fromJson(recipe, RecipeDTO.class);
        recipeAdd = FACADE.addRecipe(recipeAdd);
        return GSON.toJson(recipeAdd);
    }

    @GET
    @Path("allRecipes")
    //@RolesAllowed({"user", "admin"})
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllRecipes() {
        RecipesDTO allRecipes = FACADE.getRecipes();
        return GSON.toJson(allRecipes);
    }
}