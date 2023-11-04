package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Getter
@Setter
@Validated
public class NotificacionDto {
    private Integer id;
    @NotNull(message = "Debe ingresar un usuario")
    @Positive(message = "Debe ingresar un usuario valido")
    private int idUsuario;
    @Size(message = "Se excedió el número de caracteres del mensaje de la notificación (max 255)", max = 255)
    @NotNull(message = "Debe colocar un mensaje")
    private String mensaje;
    private Date fecha;

    public NotificacionDto(){

    }
}