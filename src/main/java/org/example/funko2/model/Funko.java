package org.example.funko2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Entidad que representa un Funko.
 * @author Aragorn7372
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="funko")
@EntityListeners(AuditingEntityListener.class)
public class Funko {
    /**
     * Identificador autogenerado de la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador único universal (UUID) del Funko.
     * Obligatorio y único.
     */
    @Column(unique = true, nullable = false)
    @NotNull
    private UUID uuid=UUID.randomUUID();

    /**
     * Nombre del Funko.
     * Obligatorio.
     */
    @Column(unique =false, nullable = false)
    @NotBlank
    private String name;

    /**
     * Precio del Funko.
     * Debe ser mayor o igual a 0.
     */
    @Column(unique =false, nullable = false)
    @Min(0)
    private Double price;

    /**
     * Cantidad en stock.
     * Debe ser mayor o igual a 0.
     */
    @Column(unique =false, nullable = false)
    @Min(0)
    private Integer cantidad;

    /**
     * URL de la imagen del Funko.
     * Obligatorio.
     */
    @Column(unique =false, nullable = false)
    @NotBlank
    private String imagen;

    /**
     * Categoría a la que pertenece el Funko.
     */
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    /**
     * Fecha de creación del registro.
     */
    @Column(unique =false, nullable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion=LocalDateTime.now();

    /**
     * Fecha de la última modificación del registro.
     */
    @Column(unique =false, nullable = false)
    @LastModifiedDate
    private LocalDateTime fechaModificacion=LocalDateTime.now();
}
