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
    Integer id;
    @NotBlank(message = "Debe ingresar un nombre")
    @Size(message = "Se excedió el número de caracteres en el nombre (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre debe contener solo caracteres alfabéticos sin espacios")
    String nombre;
    @NotBlank(message = "Debe ingresar su apellido paterno")
    @Size(message = "Se excedió el número de caracteres en el apellido paterno (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido paterno debe contener solo caracteres alfabéticos sin espacios")
    String apPat;
    @Size(message = "Se excedió el número de caracteres en el apellido materno (max 45)", max = 45)
    @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido materno debe contener solo caracteres alfabéticos sin espacios")
    String apMat;
    @NotNull(message = "Debe ingresar un número de telefono")
    @Size(message = "Número de teléfono invalido", max = 10, min = 10)
    @Pattern(regexp = "^[0-9]+$", message = "El telefono debe contener solo datos de tipo numérico")
    String telefono;
    @NotNull(message = "Debe ingresar un email")
    @Email(message = "Ingresa un email valido")
    String email;
    @NotNull(message = "Debe ingresar una contraseña")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!_.])(?=\\S+$).{8,}$", message = "La contraseña no cumple con los requisitos de seguridad:  minimo 1: mayuscula, minuscula, numero, caracter especial; 8 caracteres; sin espacios.")
    String pass;
    //List<RolAddDto> rolUsuario;

    public UsuarioDto(){

    }

}
