package facades;

import entities.Address;
import entities.CityInfo;
import entities.Person;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;

    public PersonFacadeTest() {
    }

    
    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
       facade = PersonFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = new CityInfo(2100, "Winterfell");
        CityInfo cityInfo2 = new CityInfo(3434, "Kings Landing");
        CityInfo cityInfo3 = new CityInfo(9898, "Qarth");
        Address address = new Address("Dirty row", "General bad ass", cityInfo);
        Address address2 = new Address("Castle Street", "One arm dude", cityInfo2);
        Address address3 = new Address("Essos", "Mother of Dragons", cityInfo3);
        p1 = new Person("jonsnow@got.com", "Jon", "Snow", address);
        p2 = new Person("jamiel@got.com","Jamie", "Lannister", address2);
        p3 = new Person("dragonmother@got.com", "Daenerys", "Targaryen", address3);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2); 
            em.persist(p3);
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(3, facade.getPersonCount(), "Expects 3 rows in the database");
    }

}
