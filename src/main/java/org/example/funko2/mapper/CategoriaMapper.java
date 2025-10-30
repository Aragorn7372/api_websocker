package org.example.funko2.mapper;

import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.model.Categoria;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CategoriaMapper {
    public CategoriaResponseDto categoriaToResponseDto(Categoria categoria) {
        return CategoriaResponseDto.builder()
                .id(categoria.getId())
                .name(categoria.getName())
                .build();
    }
    public Categoria categoriaRequestDtoToCategoria(CategoriaRequestDto categoriaRequestDto) {
        return Categoria.builder()
                .id(UUID.randomUUID())
                .name(categoriaRequestDto.getName())
                .fechaCreacion(LocalDateTime.now())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }
}
