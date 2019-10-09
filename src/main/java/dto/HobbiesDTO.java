/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
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
@Schema(name = "HobbiesDTO")
public class HobbiesDTO {
    
    private Set<HobbyDTO> all = new HashSet<>();

    public HobbiesDTO(Set<Hobby> hobbies) {
        hobbies.forEach((h) -> {
            all.add(new HobbyDTO(h));
        });
    }

    public Set<HobbyDTO> getAll() {
        return all;
    }
    
}
