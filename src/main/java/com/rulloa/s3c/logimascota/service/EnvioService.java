package com.rulloa.s3c.logimascota.service;
import java.util.List;
import com.rulloa.s3c.logimascota.model.Envio;
import com.rulloa.s3c.logimascota.model.Ubicacion;


public interface EnvioService {
    List<Envio> getEnvios();
    Ubicacion getUbicacion(int idenvio);
    List<Ubicacion> getHistorialUbicaciones(int idenvio);
    Envio addUbicacion(int idenvio, Ubicacion ubicacion);
    Envio addEnvio(Envio envio);
    Envio updateEnvioStatus(Envio envio);
    void deleteEnvio(int idenvio);
    Envio avanzaEstado(int idenvio);
    Envio retrocedeEstado(int idenvio);

}
