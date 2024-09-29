/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.serializableJson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

 /**
 * Default constructor is needed here for json object mapper to work!
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Vehicle {
    @Getter @Setter
    private String plateNumber;
    @Getter @Setter
    private String latitude;
    @Getter @Setter
    private String longitude;
    @Getter @Setter
    private int speed;

  
}
