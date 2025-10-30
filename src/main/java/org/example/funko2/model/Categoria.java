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
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;
    @Column(unique =false, nullable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion=LocalDateTime.now();
    @Column(unique =false, nullable = false)
    @LastModifiedDate
    private LocalDateTime fechaModificacion=LocalDateTime.now();
}
