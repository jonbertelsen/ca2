package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    
    @ManyToOne(cascade = { CascadeType.PERSIST }) // Owning 
    private Address address;
    
    @OneToMany(mappedBy="person", cascade = { CascadeType.PERSIST }) // Non owning side
    private List<Phone> phoneList = new ArrayList<>();
    
    @ManyToMany(mappedBy="persons", cascade = { CascadeType.PERSIST }) // Non Owning side
    private List<Hobby> hobbies = new ArrayList<>();
    
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

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
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
        if (!phoneList.contains(newNumber)){
            this.phoneList.add(newNumber);
            newNumber.setPerson(this);
        }
    }

    public void addHobby(String name, String description){
        Hobby newHobby = new Hobby(name, description);
        if (!hobbies.contains(newHobby)){
            this.hobbies.add(newHobby);
            newHobby.AddPerson(this);
        }
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }
   
}
