package com.rulloa.s3c.logimascota.model;

import java.time.Instant;

public class Ubicacion {
    private double latitud;
    private double longitud;
    private Instant ultimaactualizacion;

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.ultimaactualizacion = Instant.now();
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
        this.ultimaactualizacion = Instant.now();
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
        this.ultimaactualizacion = Instant.now();
    }

    public Instant getUltimaActualizacion() {
        return ultimaactualizacion;
    }
}
