package com.rulloa.s3c.logimascota.model;
import java.util.List;

public class Envio {
    private String id;
    private String direccion;
    private List<Producto> productos;
    private Ubicacion ubicacion;
    private enum Estado {
        PENDIENTE,
        EN_RUTA,
        ENTREGADO
    };
    private Estado estado;

    public Envio(String id, String direccion, List<Producto> productos) {
        this.id = id;
        this.direccion = direccion;
        this.productos = productos;
        this.estado = Estado.PENDIENTE;
        this.ubicacion = new Ubicacion(0, 0); // Inicializa con una ubicación predeterminada

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void avanzaEstado() {
        switch (estado) {
            case PENDIENTE:
                estado = Estado.EN_RUTA;
                break;
            case EN_RUTA:
                estado = Estado.ENTREGADO;
                break;
            case ENTREGADO:
                // No hacer nada, ya está en el estado final
                break;
        }
    }

    public void retrocedeEstado() {
        switch (estado) {
            case PENDIENTE:
                // No hacer nada, ya está en el estado inicial
                break;
            case EN_RUTA:
                estado = Estado.PENDIENTE;
                break;
            case ENTREGADO:
                estado = Estado.EN_RUTA;
                break;
        }
    }

}
