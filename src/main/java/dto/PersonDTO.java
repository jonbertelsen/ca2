/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jobe
 */
@Schema(name = "PersonDTO")
public class PersonDTO {
    
    private Long id;
    @Schema(required = true, example = "jamie@lannister.com")
    private String email;
    @Schema(required = true, example = "Jamie")
    private String firstName;
    @Schema(required = true, example = "Lannister")
    private String lastName;
    @Schema(required = true, example = "\"address\": {\"id\": 1,\"street\": \"Winterfell\",\"additionalInfo\": \"General badass\",\"zipCode\": 2100,\"city\": \"North\"}")
    private AddressDTO address;
    @Schema(required = true, example = "{}")
    private Set<PhoneDTO> phoneList = new HashSet<>();
    @Schema(required = true, example = "{}")
    private Set<HobbyDTO> hobbies = new HashSet<>();
    
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
