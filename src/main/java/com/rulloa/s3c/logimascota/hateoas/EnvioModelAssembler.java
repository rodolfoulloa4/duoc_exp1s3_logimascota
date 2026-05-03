package com.rulloa.s3c.logimascota.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.rulloa.s3c.logimascota.controller.EnviosController;
import com.rulloa.s3c.logimascota.model.Envio;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        Integer envioId = envio.getId();
        return EntityModel.of(envio,
                linkTo(methodOn(EnviosController.class).getEnvio(envioId)).withSelfRel(),
                linkTo(methodOn(EnviosController.class).getEnvios()).withRel("envios"),
                linkTo(methodOn(EnviosController.class).getUbicacion(envioId)).withRel("ubicacionActual"),
                linkTo(methodOn(EnviosController.class).getHistorialUbicaciones(envioId)).withRel("historialUbicaciones"),
                linkTo(methodOn(EnviosController.class).avanzaEstado(envioId)).withRel("avanzarEstado"),
                linkTo(methodOn(EnviosController.class).retrocedeEstado(envioId)).withRel("retrocederEstado"));
    }
}