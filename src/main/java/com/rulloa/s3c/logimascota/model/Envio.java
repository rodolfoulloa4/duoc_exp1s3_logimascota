package com.rulloa.s3c.logimascota.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "envio_seq")
    @SequenceGenerator(name = "envio_seq", sequenceName = "envio_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "direccion")
    private String direccion;
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "envio_producto",
        joinColumns = @JoinColumn(name = "envio_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    @JsonIgnore
    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ultimaActualizacion DESC")
    private List<Ubicacion> historialUbicaciones;

    public enum Estado {
        PENDIENTE,
        EN_RUTA,
        ENTREGADO
    };

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    public Envio(Integer id, String direccion, List<Producto> productos) {
        this.id = id;
        this.direccion = direccion;
        this.productos = productos;
        this.historialUbicaciones = new ArrayList<>();
        this.estado = Estado.PENDIENTE;
        this.agregarUbicacion(new Ubicacion(0, 0));

    }

    public Envio() {
        this.historialUbicaciones = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @JsonIgnore
    public Ubicacion getUbicacion() {
        return getUbicacionActual();
    }

    @JsonIgnore
    public void setUbicacion(Ubicacion ubicacion) {
        agregarUbicacion(ubicacion);
    }

    public Ubicacion getUbicacionActual() {
        if (historialUbicaciones == null || historialUbicaciones.isEmpty()) {
            return null;
        }
        return historialUbicaciones.get(0);
    }

    public List<Ubicacion> getHistorialUbicaciones() {
        return historialUbicaciones;
    }

    public void setHistorialUbicaciones(List<Ubicacion> historialUbicaciones) {
        this.historialUbicaciones = new ArrayList<>();
        if (historialUbicaciones != null) {
            for (Ubicacion ubicacion : historialUbicaciones) {
                agregarUbicacion(ubicacion);
            }
        }
    }

    public void agregarUbicacion(Ubicacion ubicacion) {
        if (ubicacion == null) {
            return;
        }
        ubicacion.setEnvio(this);
        historialUbicaciones.add(0, ubicacion);
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
