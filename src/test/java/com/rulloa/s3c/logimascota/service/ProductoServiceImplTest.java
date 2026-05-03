package com.rulloa.s3c.logimascota.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rulloa.s3c.logimascota.exception.ResourceNotFoundException;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.repository.ProductosRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductosRepository productosRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto existingProducto;

    @BeforeEach
    void setUp() {
        existingProducto = new Producto(3, "Transportadora", 120.0, 1.8);
    }

    @AfterEach
    void tearDown() {
        existingProducto = null;
    }

    @Test
    void shouldUpdateExistingProducto() {
        Producto cambios = new Producto(3, "Transportadora XL", 150.0, 2.1);

        when(productosRepository.findById(3)).thenReturn(Optional.of(existingProducto));
        when(productosRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto actualizado = productoService.updateProducto(cambios);

        assertEquals("Transportadora XL", actualizado.getNombre());
        assertEquals(150.0, actualizado.getPrecio());
        assertEquals(2.1, actualizado.getPeso());
    }

    @Test
    void shouldThrowWhenProductoDoesNotExist() {
        when(productosRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productoService.getProducto(99));

        assertEquals("Producto no encontrado: 99", exception.getMessage());
    }
}