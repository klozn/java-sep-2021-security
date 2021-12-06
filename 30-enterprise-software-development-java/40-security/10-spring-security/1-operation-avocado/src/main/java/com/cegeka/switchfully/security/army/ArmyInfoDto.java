package com.cegeka.switchfully.security.army;

import java.io.Serializable;

/**
 * A DTO object, used for sending data 'over the wire'
 * They're nothing more than 'dumb' data holders...
 * This object will be marshalled to JSON, or visa-versa (unmarshalling)
 */
public class ArmyInfoDto implements Serializable{

    public String country;
    public int numberOfTroops;
    public int xCoordinateOfBase;
    public int yCoordinateOfBase;

    public static ArmyInfoDto armyInfoDto() {
        return new ArmyInfoDto();
    }

    public ArmyInfoDto withCountry(String country) {
        this.country = country;
        return this;
    }

    public ArmyInfoDto withNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
        return this;
    }

    public ArmyInfoDto withxCoordinateOfBase(int xCoordinateOfBase) {
        this.xCoordinateOfBase = xCoordinateOfBase;
        return this;
    }

    public ArmyInfoDto withyCoordinateOfBase(int yCoordinateOfBase) {
        this.yCoordinateOfBase = yCoordinateOfBase;
        return this;
    }
}
