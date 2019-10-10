package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;


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
        try {
            em.getTransaction().begin();
            
                Query query;
                
                // Check if cityInfo already exists to avoid duplicates:
                query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zip AND c.city = :city", CityInfo.class);
                query.setParameter("zip", zipCode);
                query.setParameter("city", city);
                List<CityInfo> ciList = query.getResultList();
                CityInfo cityInfo;
                if (ciList.size() > 0){
                    cityInfo = ciList.get(0);  // zip+city already exist so now cityInfo is managed by JPA
                } else {
                    cityInfo = new CityInfo(zipCode, city);
                }
                
                // Check if address already exists to avoid duplicates
                query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.additionalInfo = :additional", Address.class);
                query.setParameter("street", street);
                query.setParameter("additional", additionalInfo);
                List<Address> aList = query.getResultList();
                Address address;
                if (aList.size() > 0){
                    address = aList.get(0);  // street+addinfo already exist so now address is managed by JPA
                    address.setCityInfo(cityInfo);   // Remember to set this guy
                } else {
                    address = new Address(street, additionalInfo, cityInfo);
                }
                
                Person person = new Person(email, firstName, lastName, address);
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
