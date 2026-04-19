package com.rulloa.s3c.logimascota.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.rulloa.s3c.logimascota.dto.ProductoCreateRequestDto;
import com.rulloa.s3c.logimascota.dto.ProductoUpdateRequestDto;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.service.ProductoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/productos")
@Slf4j
@Validated
public class ProductosController {

    private final ProductoService productoService;

    public ProductosController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("list")
    public List<Producto> getProductos() {
        log.debug("Listando productos");
        return productoService.getProductos();
    }

    @GetMapping("{idproducto}")
    public Producto getProducto(@PathVariable @Positive(message = "El idproducto debe ser mayor a 0") int idproducto) {
        log.debug("Buscando producto {}", idproducto);
        return productoService.getProducto(idproducto);
    }

    @PostMapping("add")
    public Producto addProducto(@Valid @RequestBody ProductoCreateRequestDto request) {
        log.info("Creando producto {}", request.getNombre());
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setPeso(request.getPeso());
        return productoService.addProducto(producto);
    }

    @PutMapping("update")
    public Producto updateProducto(@Valid @RequestBody ProductoUpdateRequestDto request) {
        log.info("Actualizando producto {}", request.getId());
        Producto producto = new Producto();
        producto.setId(request.getId());
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setPeso(request.getPeso());
        return productoService.updateProducto(producto);
    }

    @DeleteMapping("delete/{idproducto}")
    public void deleteProducto(@PathVariable @Positive(message = "El idproducto debe ser mayor a 0") int idproducto) {
        log.warn("Eliminando producto {}", idproducto);
        productoService.deleteProducto(idproducto);
    }
}
