package org.example.funko2.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para la respuesta de una categoría.
 * @author Aragorn7372
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResponseDto {
    /**
     * Identificador único de la categoría.
     */
    private UUID id;
    
    /**
     * Nombre de la categoría.
     */
    private String name;
}
