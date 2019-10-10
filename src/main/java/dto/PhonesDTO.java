/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jobe
 */
@Schema(name = "PhonesDTO")
public class PhonesDTO {
    
    private Set<PhoneDTO> all = new HashSet<>();

    public PhonesDTO(Set<Phone> phones) {
        phones.forEach((p) -> {
            all.add(new PhoneDTO(p));
        });
    }

    public Set<PhoneDTO> getAll() {
        return all;
    }

}
