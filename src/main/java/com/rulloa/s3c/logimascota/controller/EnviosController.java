package com.rulloa.s3c.logimascota.controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import com.rulloa.s3c.logimascota.dto.EnvioCreateRequestDto;
import com.rulloa.s3c.logimascota.dto.EnvioStatusUpdateRequestDto;
import com.rulloa.s3c.logimascota.dto.UbicacionRequestDto;
import com.rulloa.s3c.logimascota.model.Envio;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.model.Ubicacion;
import com.rulloa.s3c.logimascota.service.EnvioService;
import com.rulloa.s3c.logimascota.service.ProductoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;





@RestController
@RequestMapping("/envios")
@Slf4j
@Validated
public class EnviosController {
    private final EnvioService envioService;
    private final ProductoService productoService;

    public EnviosController(EnvioService envioService, ProductoService productoService) {
        this.envioService = envioService;
        this.productoService = productoService;
    }

    @GetMapping("list")
    public List<Envio> getEnvios() {
        log.debug("Listando envios");
        return envioService.getEnvios();
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
    public Envio addUbicacion(
            @PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio,
            @Valid @RequestBody UbicacionRequestDto request) {
        log.info("Agregando ubicacion para envio {}", idenvio);
        Ubicacion ubicacion = new Ubicacion(request.getLatitud(), request.getLongitud());
        return envioService.addUbicacion(idenvio, ubicacion);
    }
    
    

    @PostMapping("add")
    public Envio postMethodName(@Valid @RequestBody EnvioCreateRequestDto request) {
        log.info("Creando nuevo envio con {} productos", request.getProductoIds().size());
        List<Producto> productos = request.getProductoIds().stream()
                .map(productoService::getProducto)
                .collect(Collectors.toList());

        Envio envio = new Envio();
        envio.setDireccion(request.getDireccion());
        envio.setProductos(productos);
        envio.setEstado(Envio.Estado.PENDIENTE);

        return envioService.addEnvio(envio);
    }

    @PutMapping("update")
    public Envio updateEnvioStatus(@Valid @RequestBody EnvioStatusUpdateRequestDto request) {
        log.info("Actualizando estado para envio {} a {}", request.getId(), request.getEstado());
        Envio envio = new Envio();
        envio.setId(request.getId());
        envio.setEstado(request.getEstado());
        return envioService.updateEnvioStatus(envio);
    }

    @PatchMapping("{idenvio}/avanza")
    public Envio avanzaEstado(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.info("Avanzando estado para envio {}", idenvio);
        return envioService.avanzaEstado(idenvio);
    }

    @PatchMapping("{idenvio}/retrocede")
    public Envio retrocedeEstado(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.info("Retrocediendo estado para envio {}", idenvio);
        return envioService.retrocedeEstado(idenvio);
    }

    @DeleteMapping("delete/{idenvio}")
    public void deleteEnvio(@PathVariable @Positive(message = "El idenvio debe ser mayor a 0") int idenvio) {
        log.warn("Eliminando envio {}", idenvio);
        envioService.deleteEnvio(idenvio);
    }
    
    

}
