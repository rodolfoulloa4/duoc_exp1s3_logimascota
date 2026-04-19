package com.rulloa.s3c.logimascota.dto;

import com.rulloa.s3c.logimascota.model.Envio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EnvioStatusUpdateRequestDto {

    @NotNull(message = "El id es obligatorio")
    @Positive(message = "El id debe ser mayor a 0")
    private Integer id;

    @NotNull(message = "El estado es obligatorio")
    private Envio.Estado estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Envio.Estado getEstado() {
        return estado;
    }

    public void setEstado(Envio.Estado estado) {
        this.estado = estado;
    }
}
