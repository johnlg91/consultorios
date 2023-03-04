package org.tmed.consultoriosback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckConsultoriosControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void checkConsultorios() {
        var s = testRestTemplate.getForObject("http://localhost:" + port + "/consultorios",
                String.class);
        var expected = """
                [
                    {"id":1,"numeroDeConsultorio":1,
                     "costoPorModulo":5000,
                     "tamanioDelArea":35,
                     "equipo":"camilla",
                     "especialidades":"psiquiatria, estetica, psicopedagogia",
                     "oculto":false
                    },
                    {"id":2,"numeroDeConsultorio":2,"costoPorModulo":6500,"tamanioDelArea":45,"equipo":"camilla","especialidades":"psiquiatria","oculto":false},
                    {"id":3,"numeroDeConsultorio":3,"costoPorModulo":1000,"tamanioDelArea":40,"equipo":"Solo escritorio","especialidades":"Psiquiatria, pediatria","oculto":false},
                    {"id":4,"numeroDeConsultorio":4,"costoPorModulo":69,"tamanioDelArea":40,"equipo":"Solo escritorio","especialidades":"varias","oculto":false}]
                """.replaceAll("\n\\s*", "");
        assertEquals(expected, s);
    }

}
