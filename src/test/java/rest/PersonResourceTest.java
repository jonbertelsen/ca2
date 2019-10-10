package rest;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2, p3;
    
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
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         //Don't forget this, if you called its counterpart in @BeforeAll
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();Phone phone = new Phone("121212", "Home number");
        CityInfo cityInfo = new CityInfo(2100, "Winterfell");
        CityInfo cityInfo2 = new CityInfo(3434, "Kings Landing");
        CityInfo cityInfo3 = new CityInfo(9898, "Qarth");
        Address address = new Address("Dirty row", "General bad ass", cityInfo);
        Address address2 = new Address("Castle Street", "One arm dude", cityInfo2);
        Address address3 = new Address("Essos", "Mother of Dragons", cityInfo3);
        p1 = new Person("jonsnow@got.com", "Jon", "Snow", address);
        p1.addPhone("123123", "Home number");
        p1.addPhone("32432", "Business phone");
        p1.addHobby("Drageflyvning", "På rigtige drager");
        p1.addHobby("Bueskydning", "Efter dværge");
        p2 = new Person("jamiel@got.com","Jamie", "Lannister", address2);
        p3 = new Person("dragonmother@got.com", "Daenerys", "Targaryen", address3);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2); 
            em.persist(p3);
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
   
    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }
    
    @Test
    public void testCount() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(3));   
    }
    
    @Test
    public void getAllPersons(){
            List<PersonDTO> personsDTOs;
        
            Response response = given()
                .when().get("/person/all")
                .then()
                .contentType("application/json")
                .body("all.firstName", hasItems("Jon","Jamie","Daenerys") )
                .extract().response();
        System.out.println(response.asString());
            
    }
    
}
