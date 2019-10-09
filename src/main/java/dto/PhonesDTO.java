/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jobe
 */
public class PhonesDTO {
        private List<PhoneDTO> all = new ArrayList();

    public PhonesDTO(List<Phone> phones) {
        phones.forEach((p) -> {
            all.add(new PhoneDTO(p));
        });
    }

    public List<PhoneDTO> getAll() {
        return all;
    }

}
