package org.example.funko2.dto.funko;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de creación de un Funko.
 * @author Aragorn7372
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunkoRequestDto {
    /**
     * Nombre del Funko.
     * No puede estar vacío.
     */
    @NotBlank(message = "el nombre no puede estar vacio")
    private String name;

    /**
     * Precio del Funko.
     * No puede estar vacío y debe ser mayor o igual a 0.
     */
    @NotNull(message = "no puede estar vacio")
    @Min(value = 0,message = "no puede ser menor a 0")
    private Double price;

    /**
     * Cantidad en stock del Funko.
     * No puede estar vacío y debe ser mayor o igual a 0.
     */
    @NotNull(message = "no puede ser vacio")
    @Min(value = 0,message = "la cantidad no puede ser negativa")
    private Integer cantidad;

    /**
     * URL de la imagen del Funko.
     * No puede estar vacía.
     */
    @NotBlank(message = "la url de la imagen no puede estar vacia")
    private String imagen;

    /**
     * Categoría del Funko.
     * Debe coincidir con FANTASIA, ANIME o VIDEOJUEGOS.
     */
    @NotBlank(message = "la categoria no puede estar vacia")
    @Pattern(regexp = "FANTASIA|ANIME|VIDEOJUEGOS",message = "la categoria debe de ser una de estas tres FANTASIA,ANIME,VIDEOJUEGOS ")
    private String categoria;
}
