package org.example.funko2.mapper;

import lombok.val;
import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.model.Categoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class CategoriaMapperTest {
    private CategoriaMapper categoriaMapper=new CategoriaMapper();
    private final Categoria categoria= Categoria
            .builder()
            .id(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .name("ANIME")
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .build();
    private final CategoriaResponseDto categoriaResponseDto= CategoriaResponseDto.builder().id(categoria.getId()).name(categoria.getName()).build();
    private final CategoriaRequestDto categoriaRequestDto= CategoriaRequestDto.builder().name(categoria.getName()).build();
    @Test
    void categoriaToResponseDto() {
        val result= categoriaMapper.categoriaToResponseDto(categoria);
        assertAll(
                ()->assertEquals(categoria.getId(),result.getId()),
                ()->assertEquals(categoria.getName(),result.getName())
        );
    }

    @Test
    void categoriaRequestDtoToCategoria() {
        val result= categoriaMapper.categoriaRequestDtoToCategoria(categoriaRequestDto);
        assertAll(
                ()-> assertEquals(categoriaRequestDto.getName(),result.getName())
        );
    }
}