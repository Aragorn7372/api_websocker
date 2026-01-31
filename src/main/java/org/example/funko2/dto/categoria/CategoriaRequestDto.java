package org.example.funko2.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * DTO para la solicitud de creación de una categoría.
 * @author Aragorn7372
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaRequestDto {
    /**
     * Nombre de la categoría.
     * No puede estar en blanco.
     */
    @NotBlank
    private String name;
}
