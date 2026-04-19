package com.rulloa.s3c.logimascota.service;
import java.util.List;
import com.rulloa.s3c.logimascota.exception.ResourceNotFoundException;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<Producto> getProductos() {
        log.debug("Consultando todos los productos");
        return productosRepository.findAll();
    }

    @Override
    public Producto getProducto(int id) {
        log.debug("Buscando producto {}", id);
        return productosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    @Override
    public Producto addProducto(Producto producto) {
        log.info("Persistiendo producto {}", producto.getNombre());
        return productosRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Producto producto) {
        log.info("Actualizando producto {}", producto.getId());
        Producto existingProducto = productosRepository.findById(producto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + producto.getId()));
        existingProducto.setNombre(producto.getNombre());
        existingProducto.setPrecio(producto.getPrecio());
        existingProducto.setPeso(producto.getPeso());
        return productosRepository.save(existingProducto);
    }

    @Override
    public void deleteProducto(int id) {
        log.warn("Eliminando producto {}", id);
        Producto existingProducto = productosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        productosRepository.delete(existingProducto);
    }


}
