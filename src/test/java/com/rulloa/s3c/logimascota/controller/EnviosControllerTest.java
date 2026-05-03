package com.rulloa.s3c.logimascota.controller;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rulloa.s3c.logimascota.exception.GlobalExceptionHandler;
import com.rulloa.s3c.logimascota.hateoas.EnvioModelAssembler;
import com.rulloa.s3c.logimascota.model.Envio;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.model.Ubicacion;
import com.rulloa.s3c.logimascota.service.EnvioService;
import com.rulloa.s3c.logimascota.service.ProductoService;

@WebMvcTest(EnviosController.class)
@Import({ EnvioModelAssembler.class, GlobalExceptionHandler.class })
class EnviosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService envioService;

    @MockitoBean
    private ProductoService productoService;

    private Envio envio;

    @BeforeEach
    void setUp() {
        Producto producto = new Producto(10, "Arnes", 79.9, 0.4);
        envio = new Envio();
        envio.setId(7);
        envio.setDireccion("Calle 123 #45-67");
        envio.setProductos(List.of(producto));
        envio.setEstado(Envio.Estado.EN_RUTA);
        envio.agregarUbicacion(new Ubicacion(4.711, -74.0721));
    }

    @AfterEach
    void tearDown() {
        envio = null;
    }

    @Test
    void shouldReturnEnvioWithHateoasLinks() throws Exception {
        when(envioService.getEnvio(7)).thenReturn(envio);

        mockMvc.perform(get("/envios/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.estado").value("EN_RUTA"))
                .andExpect(jsonPath("$._links.self.href", endsWith("/envios/7")))
                .andExpect(jsonPath("$._links.ubicacionActual.href", endsWith("/envios/ubicacion/7")))
                .andExpect(jsonPath("$._links.historialUbicaciones.href", endsWith("/envios/ubicaciones/7")))
                .andExpect(jsonPath("$._links.avanzarEstado.href", endsWith("/envios/7/avanza")))
                .andExpect(jsonPath("$._links.retrocederEstado.href", endsWith("/envios/7/retrocede")));
    }
}