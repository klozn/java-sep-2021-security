package com.cegeka.switchfully.security.army;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ArmyResource.ARMY_RESOURCE_PATH)
public class ArmyResource {

    public static final String ARMY_RESOURCE_PATH = "/armies";

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{country}")
    @PreAuthorize("hasAnyRole('ROLE_PRIVATE', 'ROLE_GENERAL')")
    public ArmyInfoDto getDeployedArmyInfo(@PathVariable(value = "country") String country) {
        return ArmyInfoDto.armyInfoDto()
                .withCountry(country)
                .withNumberOfTroops(2000)
                .withxCoordinateOfBase(15)
                .withyCoordinateOfBase(20);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CIVILIAN')")
    public void joinArmy() {
        //TODO
    }

    @PostMapping(path = "/promote/{name}")
    @PreAuthorize("hasRole('ROLE_HUMAN_RELATIONSHIPS')")
    public void promotePrivate(@PathVariable(value = "name") String name) {
        //TODO
    }

    @PostMapping(path = "/discharge/{name}")
    @PreAuthorize("hasRole('ROLE_HUMAN_RELATIONSHIPS')")
    public void dischargeSoldier(@PathVariable(value = "name") String name) {
        //TODO
    }

    @GetMapping(path = "/nuke")
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    public String launchNukes() {
        return "The world ends. Not with a bang but a whimper";
    }
}
