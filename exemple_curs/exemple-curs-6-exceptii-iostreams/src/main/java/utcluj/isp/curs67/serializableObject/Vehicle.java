/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.serializableObject;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


//!!! We use lombok project to generate constructor 
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Vehicle implements Serializable{
   
    @Getter
    private String plateNumber;
    @Getter
    private String latitude;
    @Getter
    private String longitude;
    @Getter
    private int speed;
    
}
