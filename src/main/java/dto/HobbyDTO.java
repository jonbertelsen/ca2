/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author jobe
 */
@Schema(name = "HobbyDTO")
public class HobbyDTO {
    private Long id;
    private String name;
    private String description;
    
    public HobbyDTO(Hobby hobby){
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }
}
