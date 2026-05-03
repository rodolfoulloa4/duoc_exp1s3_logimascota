package com.rulloa.s3c.logimascota.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import com.rulloa.s3c.logimascota.dto.EnvioCreateRequestDto;
import com.rulloa.s3c.logimascota.dto.EnvioStatusUpdateRequestDto;
import com.rulloa.s3c.logimascota.dto.UbicacionRequestDto;
import com.rulloa.s3c.logimascota.hateoas.EnvioModelAssembler;
import com.rulloa.s3c.logimascota.model.Envio;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.model.Ubicacion;
import com.rulloa.s3c.logimascota.service.EnvioService;
import com.rulloa.s3c.logimascota.service.ProductoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;





@RestController
@RequestMapping("/envios")
@Slf4j
@Validated
public class EnviosController {
    private final EnvioService envioService;
    private final ProductoService productoService;
    private final EnvioModelAssembler envioModelAssembler;

    public EnviosController(EnvioService envioService, ProductoService productoService,
            EnvioModelAssembler envioModelAssembler) {
        this.envioService = envioService;
        this.productoService = productoService;
        this.envioModelAssembler = envioModelAssembler;
    }

    @GetMapping("list")
    public CollectionModel<EntityModel<Envio>> getEnvios() {
        log.debug("Listando envios");
        List<EntityModel<Envio>> envios = envioService.getEnvios().stream()
                .map(envioModelAssembler::toModel)
                .toList();
        return CollectionModel.of(envios,
            linkTo(methodOn(EnviosController.class).getEnvios()).withSelfRel());
    }

    @GetMapping("{idenvio}")
    public EntityModel<Envio> getEnvio(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.debug("Consultando envio {}", idenvio);
        return envioModelAssembler.toModel(envioService.getEnvio(idenvio));
    }

    @GetMapping("ubicacion/{idenvio}")
    public Ubicacion getUbicacion(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.debug("Consultando ubicacion actual para envio {}", idenvio);
        return envioService.getUbicacion(idenvio);
    }

    @GetMapping("ubicaciones/{idenvio}")
    public List<Ubicacion> getHistorialUbicaciones(
            @PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.debug("Consultando historial de ubicaciones para envio {}", idenvio);
        return envioService.getHistorialUbicaciones(idenvio);
    }

    @PostMapping("ubicacion/{idenvio}")
    public EntityModel<Envio> addUbicacion(
            @PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio,
            @Valid @RequestBody UbicacionRequestDto request) {
        log.info("Agregando ubicacion para envio {}", idenvio);
        Ubicacion ubicacion = new Ubicacion(request.getLatitud(), request.getLongitud());
        return envioModelAssembler.toModel(envioService.addUbicacion(idenvio, ubicacion));
    }
    
    

    @PostMapping("add")
    public EntityModel<Envio> postMethodName(@Valid @RequestBody EnvioCreateRequestDto request) {
        log.info("Creando nuevo envio con {} productos", request.getProductoIds().size());
        List<Producto> productos = request.getProductoIds().stream()
                .map(productoService::getProducto)
                .collect(Collectors.toList());

        Envio envio = new Envio();
        envio.setDireccion(request.getDireccion());
        envio.setProductos(productos);
        envio.setEstado(Envio.Estado.PENDIENTE);

        return envioModelAssembler.toModel(envioService.addEnvio(envio));
    }

    @PutMapping("update")
    public EntityModel<Envio> updateEnvioStatus(@Valid @RequestBody EnvioStatusUpdateRequestDto request) {
        log.info("Actualizando estado para envio {} a {}", request.getId(), request.getEstado());
        Envio envio = new Envio();
        envio.setId(request.getId());
        envio.setEstado(request.getEstado());
        return envioModelAssembler.toModel(envioService.updateEnvioStatus(envio));
    }

    @PatchMapping("{idenvio}/avanza")
    public EntityModel<Envio> avanzaEstado(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.info("Avanzando estado para envio {}", idenvio);
        return envioModelAssembler.toModel(envioService.avanzaEstado(idenvio));
    }

    @PatchMapping("{idenvio}/retrocede")
    public EntityModel<Envio> retrocedeEstado(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.info("Retrocediendo estado para envio {}", idenvio);
        return envioModelAssembler.toModel(envioService.retrocedeEstado(idenvio));
    }

    @DeleteMapping("delete/{idenvio}")
    public void deleteEnvio(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.warn("Eliminando envio {}", idenvio);
        envioService.deleteEnvio(idenvio);
    }
    
    

}
