/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;
import entities.Address;
import entities.Hobby;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jobe
 */
public class PersonDTO {
    
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private AddressDTO address;
    private List<PhoneDTO> phoneList = new ArrayList<>();
    private List<HobbyDTO> hobbies = new ArrayList<>();
    
    public PersonDTO(Person person){
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = new AddressDTO(person.getAddress());
        this.hobbies = new HobbiesDTO(person.getHobbies()).getAll();
        this.phoneList = new PhonesDTO(person.getPhoneList()).getAll();
    }
    
}
