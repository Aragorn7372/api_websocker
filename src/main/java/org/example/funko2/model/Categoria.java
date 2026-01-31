package org.example.funko2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Entidad que representa una Categoría de Funkos.
 * @author Aragorn7372
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {
    /**
     * Identificador único de la categoría.
     */
    @Id
    private UUID id;

    /**
     * Nombre de la categoría.
     * Debe ser único y no nulo.
     */
    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    /**
     * Fecha de creación del registro.
     * Se asigna automáticamente al crear.
     */
    @Column(unique =false, nullable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion=LocalDateTime.now();

    /**
     * Fecha de la última modificación del registro.
     * Se actualiza automáticamente.
     */
    @Column(unique =false, nullable = false)
    @LastModifiedDate
    private LocalDateTime fechaModificacion=LocalDateTime.now();
}
