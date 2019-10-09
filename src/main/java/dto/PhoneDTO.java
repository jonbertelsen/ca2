/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Phone;

/**
 *
 * @author jobe
 */
public class PhoneDTO {
    private Long id;
    private String number;
    private String description;
    
    public PhoneDTO(Phone phone){
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }
    
}
