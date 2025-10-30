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
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="funko")
@EntityListeners(AuditingEntityListener.class)
public class Funko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotNull
    private UUID uuid=UUID.randomUUID();
    @Column(unique =false, nullable = false)
    @NotBlank
    private String name;
    @Column(unique =false, nullable = false)
    @Min(0)
    private Double price;
    @Column(unique =false, nullable = false)
    @Min(0)
    private Integer cantidad;
    @Column(unique =false, nullable = false)
    @NotBlank
    private String imagen;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    @Column(unique =false, nullable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion=LocalDateTime.now();
    @Column(unique =false, nullable = false)
    @LastModifiedDate
    private LocalDateTime fechaModificacion=LocalDateTime.now();
}
