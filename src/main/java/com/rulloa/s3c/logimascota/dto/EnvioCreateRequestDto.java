package com.rulloa.s3c.logimascota.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EnvioCreateRequestDto {

    @NotBlank(message = "La direccion es obligatoria")
    @Size(min = 5, max = 180, message = "La direccion debe tener entre 5 y 180 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .,#-]+$", message = "La direccion contiene caracteres no permitidos")
    private String direccion;

    @NotEmpty(message = "Debe incluir al menos un producto")
    private List<@NotNull(message = "El id de producto es obligatorio") @Positive(message = "El id de producto debe ser mayor a 0") Integer> productoIds;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Integer> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Integer> productoIds) {
        this.productoIds = productoIds;
    }
}
