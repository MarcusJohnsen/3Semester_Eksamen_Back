package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RecipeDTO;
import dto.RecipesDTO;
import errorhandling.InvalidInputException;
import utils.EMF_Creator;
import facades.RecipeFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("all")
    @RolesAllowed({"user", "admin"})
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllRecipes() {
        RecipesDTO allRecipes = FACADE.getRecipes();
        return GSON.toJson(allRecipes);
    }
    
    @PUT
    @Path("/edit")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    public String editRecipe(String recipe) {
        RecipeDTO changedRecipe = GSON.fromJson(recipe, RecipeDTO.class);
        FACADE.editRecipe(changedRecipe);
        return GSON.toJson(changedRecipe);
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public void deleteRecipe(@PathParam("id")long id) {
        FACADE.deleteRecipe(id);
    }
    
    //opgaven siger "the chef" gerne vil.. jeg antager han er admin
    //jeg er ikke i tvivl om dette er... uskøn kode, men det bedste jeg kunne finde på under presset
    @PUT
    @Path("/weeklyPlan/{id}")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    public String addRecipeToWeeklyPlan(@PathParam("id")long id, String recipe) throws InvalidInputException {
        RecipeDTO addedRecipe = GSON.fromJson(recipe, RecipeDTO.class);
        RecipeDTO recipeResult = FACADE.addRecipeToWeeklyMenuPlan(id, addedRecipe.getId());
        return GSON.toJson(recipeResult);
    }
}