/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.CityInfo;

/**
 *
 * @author jobe
 */
public class CityInfoDTO {
    private Long id;
    private int zipCode;
    private String city;

    public CityInfoDTO(CityInfo cityInfoObj) {
        this.id = cityInfoObj.getId();
        this.zipCode = cityInfoObj.getZipCode();
        this.city = cityInfoObj.getCity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
