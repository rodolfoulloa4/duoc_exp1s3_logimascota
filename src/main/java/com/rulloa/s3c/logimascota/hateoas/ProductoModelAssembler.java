package com.rulloa.s3c.logimascota.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.rulloa.s3c.logimascota.controller.ProductosController;
import com.rulloa.s3c.logimascota.model.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductosController.class).getProducto(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductosController.class).getProductos()).withRel("productos"));
    }
}