package org.example.funko2.mapper;

import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.model.Categoria;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Mapper para la conversión entre DTOs y la entidad Categoria.
 * @author Aragorn7372
 */
@Component
public class CategoriaMapper {
    /**
     * Convierte una entidad Categoria a CategoriaResponseDto.
     * @param categoria Entidad Categoria.
     * @return CategoriaResponseDto.
     */
    public CategoriaResponseDto categoriaToResponseDto(Categoria categoria) {
        return CategoriaResponseDto.builder()
                .id(categoria.getId())
                .name(categoria.getName())
                .build();
    }
    /**
     * Convierte un CategoriaRequestDto a una entidad Categoria.
     * @param categoriaRequestDto DTO de petición.
     * @return Entidad Categoria.
     */
    public Categoria categoriaRequestDtoToCategoria(CategoriaRequestDto categoriaRequestDto) {
        return Categoria.builder()
                .id(UUID.randomUUID())
                .name(categoriaRequestDto.getName())
                .fechaCreacion(LocalDateTime.now())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }
}
