package org.tmed.consultoriosback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = { "spring.datasource.hikari.auto-commit=true" })
public class PropertyTest {

    @Value("${spring.datasource.url}") private String url;
    @Value("${spring.datasource.hikari.auto-commit}") private String xx;

    @Test
    public void shouldSpringBootTestAnnotation_overridePropertyValues() {
        assertEquals("jdbc:mysql://localhost:3306/CONSULTORIOS_SCHEMA_BD", url);
        assertEquals("true", xx);
    }
}