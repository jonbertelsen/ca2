/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author jobe
 */
@Schema(name = "AddressDTO")
public class AddressDTO {
    
    
    private Long id;
    @Schema(required = true, example = "Storegade 11")
    private String street;
    @Schema(required = true, example = "Et skummelt sted")
    private String additionalInfo;
    @Schema(required = true, example = "5600")
    private int zipCode;
    @Schema(required = true, example = "Rønne")
    private String city;
    
    public AddressDTO(Address address){
        this.id = address.getId();
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        if (address.getCityInfo() != null){
            this.zipCode = address.getCityInfo().getZipCode();
            this.city = address.getCityInfo().getCity();
        }
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
 
    
    
}
