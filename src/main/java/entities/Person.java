package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE}) // Owning 
    private Address address;
    
    @OneToMany(mappedBy="person", cascade = { CascadeType.PERSIST }) // Non owning side
    private Set<Phone> phoneList = new HashSet<>();
    
    @ManyToMany(mappedBy="persons", cascade = { CascadeType.PERSIST }) // Non Owning side
    private Set<Hobby> hobbies = new HashSet<>();
    
    public Person() {
    }

    public Person(String email, String firstName, String lastName, Address address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(Set<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addAddress(Address address) {
        this.address = address;
        address.addPerson(this);

    }
    
    public void addPhone(String number, String description){
        Phone newNumber = new Phone(number, description);
        newNumber.setPerson(this);    
        this.phoneList.add(newNumber);
            
    }

    public void addHobby(String name, String description){
        Hobby newHobby = new Hobby(name, description);
        newHobby.AddPerson(this);
        this.hobbies.add(newHobby);
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.firstName);
        hash = 29 * hash + Objects.hashCode(this.lastName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }
   
}
