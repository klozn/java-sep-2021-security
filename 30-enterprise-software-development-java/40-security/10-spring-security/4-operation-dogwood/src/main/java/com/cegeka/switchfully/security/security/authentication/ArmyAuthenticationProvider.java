package com.cegeka.switchfully.security.security.authentication;

import com.cegeka.switchfully.security.security.authentication.external.ExternalAuthentication;
import com.cegeka.switchfully.security.security.authentication.external.FakeAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArmyAuthenticationProvider implements AuthenticationProvider {

    private final FakeAuthenticationService fakeAuthenticationService;

    @Autowired
    public ArmyAuthenticationProvider(FakeAuthenticationService fakeAuthenticationService) {
        this.fakeAuthenticationService = fakeAuthenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ExternalAuthentication user = fakeAuthenticationService.getUser(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
        if(user != null){
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), rolesToGrantedAuthorities(user.getRoles()));
        }
        throw new BadCredentialsException("The provided credentials were invalid.");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Collection<? extends GrantedAuthority> rolesToGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
