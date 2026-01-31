package org.example.funko2.dto.funko;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la actualización parcial de un Funko.
 * 
 * @author Aragorn7372
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunkoPatchRequestDto {
    /**
     * Nombre del Funko (opcional).
     */
    private String name;

    /**
     * Precio del Funko (opcional).
     * Si se proporciona, no puede ser menor a 0.
     */
    @Min(value = 0, message = "no puede ser menor a 0")
    private Double price;

    /**
     * Cantidad en stock (opcional).
     * Si se proporciona, no puede ser negativa.
     */
    @Min(value = 0, message = "la cantidad no puede ser negativa")
    private Integer cantidad;

    /**
     * URL de la imagen (opcional).
     */
    private String imagen;

    /**
     * Categoría del Funko (opcional).
     * Si se proporciona, debe ser válida (FANTASIA, ANIME, VIDEOJUEGOS).
     */
    @Pattern(regexp = "FANTASIA|ANIME|VIDEOJUEGOS", message = "la categoria debe de ser una de estas tres FANTASIA,ANIME,VIDEOJUEGOS ")
    private String categoria;
}
