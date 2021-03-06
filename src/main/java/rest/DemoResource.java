package rest;

import com.google.gson.Gson;
import dto.IngredientDTO;
import dto.ItemDTO;
import dto.ApiDTO;
import dto.StorageDTO;
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
import utils.HttpUtils;

@Path("info")
public class DemoResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private final static Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            List<User> users = em.createQuery("select user from User user").getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("external")
    public String getFromExternalAPI() {

        String[] fetchStrings = new String[]{
        };
        String[] fetched = new String[fetchStrings.length];
        ExecutorService workingJack = Executors.newFixedThreadPool(fetchStrings.length);

        try {
            for (int i = 0; i < fetchStrings.length; i++) {
                final int n = i;
                Runnable task = () -> {
                    try {
                        fetched[n] = HttpUtils.fetchData(fetchStrings[n]);
                    } catch (IOException ex) {
                        Logger.getLogger(DemoResource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                workingJack.submit(task);
            }

            workingJack.shutdown();
            workingJack.awaitTermination(5, TimeUnit.SECONDS);

            IngredientDTO chuck = GSON.fromJson(fetched[0], IngredientDTO.class);
            ItemDTO dad = GSON.fromJson(fetched[1], ItemDTO.class);
            StorageDTO officalJoke = GSON.fromJson(fetched[2], StorageDTO.class);
            
            ApiDTO apis = new ApiDTO(chuck, dad, officalJoke);
            return GSON.toJson(apis);
        } catch (InterruptedException ex) {
            Logger.getLogger(DemoResource.class.getName()).log(Level.SEVERE, null, ex);
            return "{\"info\":\"Error\"}";
        }
    }

}
