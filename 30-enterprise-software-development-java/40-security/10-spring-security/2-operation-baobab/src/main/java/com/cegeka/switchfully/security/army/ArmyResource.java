package com.cegeka.switchfully.security.army;

import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ArmyResource.ARMY_RESOURCE_PATH)
public class ArmyResource {

    public static final String ARMY_RESOURCE_PATH = "/armies";

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{country}")
    public ArmyInfoDto getDeployedArmyInfo(@PathVariable(value = "country") String country) {
        return ArmyInfoDto.armyInfoDto()
                .withCountry(country)
                .withNumberOfTroops(2000)
                .withxCoordinateOfBase(15)
                .withyCoordinateOfBase(20);
    }

    @PostMapping
    public void joinArmy() {
        //TODO
    }

    @PostMapping(path = "/promote/{name}")
    public void promotePrivate(@PathVariable(value = "name") String name) {
        //TODO
    }

    @PostMapping(path = "/discharge/{name}")
    public void dischargeSoldier(@PathVariable(value = "name") String name) {
        //TODO
    }

    @GetMapping(path = "/nuke")
    public String launchNukes() {
        return "The world ends. Not with a bang but a whimper";
    }
}
