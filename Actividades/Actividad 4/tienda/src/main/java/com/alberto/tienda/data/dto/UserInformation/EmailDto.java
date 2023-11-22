package com.alberto.tienda.data.dto.UserInformation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class EmailDto {
    @NotNull(message = "Debe ingresar un id de usuario válido")
    @Positive(message = "Debe ingresar un id de usuario válido")
    private int id;

    @NotNull(message = "Debe ingresar un email")
    @NotBlank(message = "Debe ingresar un email válido")
    @Email(message = "Debe ingresar un email válido")
    private String email;

    public EmailDto(){

    }
}


