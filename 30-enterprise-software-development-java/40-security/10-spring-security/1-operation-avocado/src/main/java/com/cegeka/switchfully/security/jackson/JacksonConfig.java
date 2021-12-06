package com.cegeka.switchfully.security.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Configuration
public class JacksonConfig {

    /**
     * The ObjectMapper is used by - and part of - the Jackson library for mapping our
     * Java classes (in our cases, our *Dto.java classes) to JSON and visa-versa.
     * We specify here that it should use the fields, not the getters/setters for mapping.
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(
                mapper.getSerializationConfig().getDefaultVisibilityChecker()
                        .withFieldVisibility(ANY)
                        .withGetterVisibility(NONE)
                        .withIsGetterVisibility(NONE)
                        .withSetterVisibility(NONE)
                        .withCreatorVisibility(NONE)
        );
        return mapper;
    }
}
