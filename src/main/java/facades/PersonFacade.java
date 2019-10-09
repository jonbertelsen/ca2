package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public long getPersonCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long personCount = (long)em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        }finally{  
            em.close();
        }
    }
    
    public PersonDTO addPerson(String email, String firstName, String lastName, String street, String additionalInfo, int zipCode, String city){
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = new CityInfo(zipCode, city);
        Address address = new Address(street, additionalInfo, cityInfo);
        Person person = new Person(email, firstName, lastName, address);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        } 
    }
    
    public PersonsDTO getAllPersons(){
    EntityManager em = emf.createEntityManager();
        try {
            List<Person> personList = em.createQuery("SELECT p FROM Person p", Person.class).getResultList(); 
            PersonsDTO pDTOlist =  new PersonsDTO(personList);
                return (pDTOlist);
        } finally {  
                em.close();
        }
        
    }

}
