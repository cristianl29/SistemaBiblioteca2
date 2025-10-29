package com.cristianlopera.biblioteca.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO {
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;

    @NotNull(message = "El año es requerido")
    @Min(value = 1000, message = "El año parece inválido")
    @Max(value = 9999, message = "El año parece inválido")
    private Integer anio;
}
