package com.cegeka.switchfully.security;

import com.cegeka.switchfully.security.security.ArmyAuthenticationEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ArmyAuthenticationEntryPointTest {

    private ArmyAuthenticationEntryPoint entrypoint;

    private ArmyAuthenticationEntryPointTest() {
        entrypoint = new ArmyAuthenticationEntryPoint();
    }

    @Test
    void commence_shouldSetUnauthorisedStatusAndHeader() {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        entrypoint.commence(request, response, null);

        assertThat(response.getHeader("WWW-Authenticate")).isEqualTo("Basic realm=" + ArmyAuthenticationEntryPoint.NAME_OF_REALM);
        assertThat(response.getStatus()).isEqualTo(401);
    }
}
