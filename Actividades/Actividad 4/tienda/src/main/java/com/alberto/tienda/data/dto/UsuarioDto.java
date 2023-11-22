package com.alberto.tienda.data.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Getter
@Setter
@Validated
public class UsuarioDto{
    private Integer id;
    @NotBlank(message = "Debe ingresar un nombre")
    @Size(message = "Se excedió el número de caracteres en el nombre (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre debe contener solo caracteres alfabéticos sin espacios")
    private String nombre;
    @NotBlank(message = "Debe ingresar su apellido paterno")
    @Size(message = "Se excedió el número de caracteres en el apellido paterno (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido paterno debe contener solo caracteres alfabéticos sin espacios")
    private String apPat;
    @Size(message = "Se excedió el número de caracteres en el apellido materno (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido materno debe contener solo caracteres alfabéticos sin espacios")
    private String apMat;
    @NotNull(message = "Debe ingresar un número de telefono")
    @Size(message = "Número de teléfono invalido", max = 10, min = 10)
    @Pattern(regexp = "^[0-9]+$", message = "El telefono debe contener solo datos de tipo numérico")
    private String telefono;
    @NotNull(message = "Debe ingresar un email")
    @NotBlank(message = "Ingresa un email valido")
    @Email(message = "Ingresa un email valido")
    private String email;
    @NotNull(message = "Debe ingresar una contraseña")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!_.])(?=\\S+$).{8,}$", message = "La contraseña no cumple con los requisitos de seguridad:  minimo 1: mayuscula, minuscula, numero, caracter especial; 8 caracteres; sin espacios.")
    private String pass;
    //List<RolAddDto> rolUsuario;

    public UsuarioDto(){

    }

}
