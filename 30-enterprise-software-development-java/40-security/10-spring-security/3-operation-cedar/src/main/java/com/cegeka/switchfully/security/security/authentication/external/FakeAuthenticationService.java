package com.cegeka.switchfully.security.security.authentication.external;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class FakeAuthenticationService {

    private List<ExternalAuthentication> externalAuthentications = newArrayList(
            ExternalAuthentication.externalAuthentication().withUsername("ZWANETTA").withPassword("WORST").withRoles(newArrayList("CIVILIAN")),
            ExternalAuthentication.externalAuthentication().withUsername("JMILLER").withPassword("THANKS").withRoles(newArrayList("PRIVATE")),
            ExternalAuthentication.externalAuthentication().withUsername("UNCLE").withPassword("SAM").withRoles(newArrayList("HUMAN_RELATIONSHIPS")),
            ExternalAuthentication.externalAuthentication().withUsername("GENNY").withPassword("RALLY").withRoles(newArrayList("GENERAL"))
    );

    public Optional<ExternalAuthentication> getUser(String username, String password) {
        return externalAuthentications.stream()
                .filter(externalAuthentication -> externalAuthentication.getUsername().equals(username))
                .filter(externalAuthentication -> externalAuthentication.getPassword().equals(password))
                .findFirst();
    }
}
