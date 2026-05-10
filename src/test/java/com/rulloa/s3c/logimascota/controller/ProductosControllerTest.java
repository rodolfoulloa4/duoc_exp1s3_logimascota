package com.rulloa.s3c.logimascota.controller;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rulloa.s3c.logimascota.exception.GlobalExceptionHandler;
import com.rulloa.s3c.logimascota.hateoas.ProductoModelAssembler;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.service.ProductoService;

@WebMvcTest(ProductosController.class)
@Import({ ProductoModelAssembler.class, GlobalExceptionHandler.class })
class ProductosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto(1, "Collar Premium", 49.9, 0.3);
    }

    @AfterEach
    void tearDown() {
        producto = null;
    }

    @Test
    void shouldReturnProductoWithHateoasLinks() throws Exception {
        when(productoService.getProducto(1)).thenReturn(producto);

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Collar Premium"))
                .andExpect(jsonPath("$._links.self.href", endsWith("/productos/1")))
                .andExpect(jsonPath("$._links.productos.href", endsWith("/productos/list")));
    }

    @Test
    void shouldRejectInvalidProductoPayload() throws Exception {
        String payload = """
                {
                  \"nombre\": \"x\",
                  \"precio\": 0,
                  \"peso\": -1
                }
                """;

        mockMvc.perform(post("/productos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.validations.nombre").exists())
                .andExpect(jsonPath("$.validations.precio").value("El precio debe ser mayor a 0"))
                .andExpect(jsonPath("$.validations.peso").value("El peso debe ser mayor a 0"));
    }

    @Test
    void shouldRejectNonPositiveProductoIdPathParam() throws Exception {
        mockMvc.perform(get("/productos/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message", containsString("idproducto")));
    }
}