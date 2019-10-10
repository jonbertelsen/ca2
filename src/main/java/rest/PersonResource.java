package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import utils.EMF_Creator;
import facades.PersonFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("person")
@OpenAPIDefinition(
            info = @Info(
                    title = "CA2 ",
                    version = "0.1",
                    description = "Simple API for the CA2",        
                    contact = @Contact( name = "Jon Bertelsen", email = "jobe@cphbusiness.dk")
            ),
          tags = {
                    @Tag(name = "Person", description = "API related to CA2")
              
            },
            servers = {
                    @Server(
                            description = "For Local host testing",
                            url = "http://localhost:8080/ca2"
                    ),
                    @Server(
                            description = "Server API",
                            url = "https://cba.scenius.dk/ca2"
                    )           
            }
    )

public class PersonResource {

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV,EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE =  PersonFacade.getFacade(emf);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get all persons in the system",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonsDTO.class))),
                    @ApiResponse(responseCode = "200", description = "Get all persons in system")})
    public String getAllPersons() {
        PersonsDTO persons = FACADE.getAllPersons();
        return GSON.toJson(persons);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Person by ID",
            tags = {"person"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The requested person"),                       
                    @ApiResponse(responseCode = "400", description = "Person not found")})
    public String getPersonById(@PathParam("id") int id) {
        return GSON.toJson(FACADE.addPerson("jonsnow@got.com", "Jon","Snow", "Winterfell", "General badass", 2100, "North"));
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

}
