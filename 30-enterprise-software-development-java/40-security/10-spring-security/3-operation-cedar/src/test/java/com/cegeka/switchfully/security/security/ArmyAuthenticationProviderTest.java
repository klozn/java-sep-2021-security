package com.cegeka.switchfully.security.security;

import com.cegeka.switchfully.security.security.authentication.external.ExternalAuthentication;
import com.cegeka.switchfully.security.security.authentication.external.FakeAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class ArmyAuthenticationProviderTest {

    private FakeAuthenticationService authenticationServiceMock;
    private ArmyAuthenticationProvider armyAuthenticationProvider;

    @BeforeEach
    void setUp() {
        authenticationServiceMock = Mockito.mock(FakeAuthenticationService.class);
        armyAuthenticationProvider = new ArmyAuthenticationProvider(authenticationServiceMock);
    }

/*
    @Test
    void authenticate_whenValidAuthentication_returnsAuthenticationObject() {
        // GIVEN
        ArmyAuthentication authentication = new ArmyAuthentication(
                ExternalAuthentication.externalAuthentication()
                        .withUsername("user")
                        .withPassword("hello")
                        .withRoles(List.of("boss", "ceo"))
        );
        // WHEN
        Mockito.when(authenticationServiceMock.getUser("user", "hello"))
                .thenReturn(Optional.of(ExternalAuthentication.externalAuthentication()
                        .withUsername("user").withPassword("hello").withRoles(List.of("boss", "ceo"))));
        Authentication authenticated = armyAuthenticationProvider.authenticate(authentication);
        // THEN
        assertEquals("user", authenticated.getName());
        assertEquals("hello", authenticated.getCredentials().toString());
    }

    @Test
    void authenticate_whenInvalidAuthentication_throwBadCredentialsException() {
        // GIVEN
        ArmyAuthentication authentication = new ArmyAuthentication(
                ExternalAuthentication.externalAuthentication()
                        .withUsername("user")
                        .withPassword("hello")
                        .withRoles(List.of("boss", "ceo"))
        );
        // WHEN
        Mockito.when(authenticationServiceMock.getUser("user", "hello"))
                .thenReturn(Optional.empty());
        // THEN
        assertThrows(BadCredentialsException.class, () -> armyAuthenticationProvider.authenticate(authentication));
    }
}*/
}
