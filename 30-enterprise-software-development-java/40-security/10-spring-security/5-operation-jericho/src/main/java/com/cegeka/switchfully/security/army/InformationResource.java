package com.cegeka.switchfully.security.army;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = InformationResource.INFORMATION_RESOURCE_PATH)
public class InformationResource {

    public static final String INFORMATION_RESOURCE_PATH = "/information";

    @GetMapping
    public String getGeneralInformation() {
        return "In the army we have guns and bullets.";
    }
}
