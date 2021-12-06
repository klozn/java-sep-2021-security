package com.cegeka.switchfully.security.security;

import com.cegeka.switchfully.security.security.authentication.external.ExternalAuthentication;
import com.cegeka.switchfully.security.security.authentication.external.FakeAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ArmyAuthenticationProvider implements AuthenticationProvider {

    private final FakeAuthenticationService authenticationService;

    @Autowired
    public ArmyAuthenticationProvider(FakeAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<ExternalAuthentication> optionalExternalAuthentication = authenticationService.getUser(
                authentication.getName(), authentication.getCredentials().toString());
        if (optionalExternalAuthentication.isEmpty()) {
            throw new BadCredentialsException("Bad credentials given");
        }
        ExternalAuthentication externalAuthentication = optionalExternalAuthentication.get();
        return new UsernamePasswordAuthenticationToken(
                externalAuthentication.getUsername(), externalAuthentication.getPassword(),
                externalAuthentication.getRoles().stream().map(ArmyGrantedAuthority::new).collect(Collectors.toList()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
