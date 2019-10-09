/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jobe
 */
@Schema(name = "PersonsDTO")
public class PersonsDTO {
    
    @Schema(required = true, example = "{" +
        "  { \"id\": 1,\"email\": \"jonsnow@got.com\"," +
        "    \"firstName\": \"Jon\"," +
         "   \"lastName\": \"Snow\"," +
         "   \"address\": {" +
         "     \"id\": 1," +
         "     \"street\": \"Winterfell\"," +
         "     \"additionalInfo\": \"General badass\"," +
         "     \"zipCode\": 2100," +
         "     \"city\": \"North\"" +
         "   }," +
          "  \"phoneList\": []," +
          "  \"hobbies\": [] } ]) })")

    private Set<PersonDTO> all = new HashSet<>();

    public PersonsDTO(List<Person> persons) {
        persons.forEach((p) -> {
            all.add(new PersonDTO(p));
        });
    }

    public Set<PersonDTO> getAll() {
        return all;
    }
    
}
