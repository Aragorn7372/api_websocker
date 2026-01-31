package org.example.funko2.dto.funko;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para la respuesta de un Funko.
 * 
 * @author Aragorn7372
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunkoResponseDto {

    /**
     * Identificador numérico del Funko.
     */
    private long id;

    /**
     * Identificador único universal (UUID) del Funko.
     */
    private UUID uuid;

    /**
     * Nombre del Funko.
     */
    private String name;

    /**
     * Precio del Funko.
     */
    private Double price;

    /**
     * Cantidad en stock del Funko.
     */
    private Integer cantidad;

    /**
     * URL de la imagen del Funko.
     */
    private String imagen;

    /**
     * Categoría a la que pertenece el Funko.
     */
    private String categoria;
}
