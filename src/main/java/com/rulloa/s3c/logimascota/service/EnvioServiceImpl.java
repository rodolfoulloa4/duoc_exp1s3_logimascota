package com.rulloa.s3c.logimascota.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rulloa.s3c.logimascota.exception.ResourceNotFoundException;
import com.rulloa.s3c.logimascota.model.Envio;
import com.rulloa.s3c.logimascota.model.Ubicacion;
import com.rulloa.s3c.logimascota.repository.EnviosReposiroty;

import lombok.extern.slf4j.Slf4j;




@Service
@Slf4j
public class EnvioServiceImpl implements EnvioService {
   @Autowired
   private EnviosReposiroty enviosReposiroty;

    @Override
    public List<Envio> getEnvios() {
        log.debug("Consultando todos los envios");
        return enviosReposiroty.findAll();
    }

    @Override
    public Ubicacion getUbicacion(int idenvio) {
        log.debug("Consultando ubicacion actual para envio {}", idenvio);
        Envio env = enviosReposiroty.findById(idenvio)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + idenvio));
        return env.getUbicacionActual();
    }

    @Override
    public List<Ubicacion> getHistorialUbicaciones(int idenvio) {
        log.debug("Consultando historial de ubicaciones para envio {}", idenvio);
        Envio env = enviosReposiroty.findById(idenvio)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + idenvio));
        return new ArrayList<>(env.getHistorialUbicaciones());
    }

    @Override
    public Envio addUbicacion(int idenvio, Ubicacion ubicacion) {
        log.info("Agregando nueva ubicacion al envio {}", idenvio);
        Envio env = enviosReposiroty.findById(idenvio)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + idenvio));
        env.agregarUbicacion(ubicacion);
        return enviosReposiroty.save(env);
    }

    @Override
    public Envio addEnvio(Envio envio) {
        log.info("Persistiendo nuevo envio");
        return enviosReposiroty.save(envio);
    }

    @Override
    public Envio updateEnvioStatus(Envio envio) {
        log.info("Actualizando estado para envio {}", envio.getId());
        Envio existingEnvio = enviosReposiroty.findById(envio.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + envio.getId()));
        existingEnvio.setEstado(envio.getEstado());
        return enviosReposiroty.save(existingEnvio);
    }

    @Override
    public void deleteEnvio(int idenvio) {
        log.warn("Eliminando envio {}", idenvio);
        Envio existingEnvio = enviosReposiroty.findById(idenvio)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + idenvio));
        enviosReposiroty.delete(existingEnvio);
        
    }

    @Override
    public Envio avanzaEstado(int idenvio) {
        log.info("Avanzando estado para envio {}", idenvio);
        Envio existingEnvio = enviosReposiroty.findById(idenvio)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + idenvio));
        existingEnvio.avanzaEstado();
        return enviosReposiroty.save(existingEnvio);
    }

    @Override
    public Envio retrocedeEstado(int idenvio) { 
        log.info("Retrocediendo estado para envio {}", idenvio);
        Envio existingEnvio = enviosReposiroty.findById(idenvio)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + idenvio));
        existingEnvio.retrocedeEstado();
        return enviosReposiroty.save(existingEnvio);
    }

}
