package org.example.funko2.mapper;

import lombok.val;
import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FunkoMapperTest {
    FunkoMapper funkoMapper=new FunkoMapper();
    private final Categoria categoria= Categoria
            .builder()
            .id(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .name("ANIME")
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .build();
    private final Funko funko= Funko.builder()
            .id(1L)
            .name("hola")
            .price(1.0)
            .imagen("imagen.png")
            .cantidad(3)
            .uuid(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .categoria(categoria)
            .build();
    private final FunkoResponseDto funkoResponse=FunkoResponseDto.builder()
            .id(funko.getId())
            .name(funko.getName())
            .price(funko.getPrice())
            .imagen(funko.getImagen())
            .cantidad(funko.getCantidad())
            .uuid(funko.getUuid())
            .categoria("ANIME").build();
    private final FunkoRequestDto funkoRequestDto= FunkoRequestDto.builder()
            .name(funko.getName())
            .price(funko.getPrice())
            .imagen(funko.getImagen())
            .categoria("ANIME")
            .cantidad(funko.getCantidad())
            .build();
    private final FunkoPatchRequestDto funkoPatchRequestDto= FunkoPatchRequestDto.builder()
            .name(funko.getName())
            .price(funko.getPrice())
            .imagen(funko.getImagen())
            .categoria("ANIME")
            .cantidad(funko.getCantidad())
            .build();
    @Test
    void funkoRequestDtoToFunko() {
        val result= funkoMapper.funkoRequestDtoToFunko(funkoRequestDto);
        assertAll(
                ()-> assertEquals(result.getName(),funko.getName(),"deberian tener el mismo nombre"),
                ()->assertEquals(result.getCantidad(),funko.getCantidad(),"deberian tener la misma cantidad"),
                ()-> assertEquals(result.getPrice(),funko.getPrice(),"deberian tener el mismo price"),
                ()-> assertEquals(result.getImagen(),funko.getImagen(),"deberian tener la misma imagen"),
                ()-> assertEquals(result.getCategoria().getName(),funko.getCategoria().getName(),"deberian tener la misma categoria")
        );
    }

    @Test
    void funkoToFunkoResponseDto() {
        val result= funkoMapper.funkoToFunkoResponseDto(funko);
        assertAll(
                ()-> assertEquals(result.getId(),funkoResponse.getId()),
                ()-> assertEquals(result.getName(),funkoResponse.getName()),
                ()-> assertEquals(result.getCategoria(),funkoResponse.getCategoria()),
                ()-> assertEquals(result.getUuid(),funkoResponse.getUuid()),
                ()-> assertEquals(result.getCantidad(),funkoResponse.getCantidad()),
                ()-> assertEquals(result.getImagen(), funkoResponse.getImagen()),
                () -> assertEquals(result.getPrice(), funkoResponse.getPrice())
        );
    }

    @Test
    void funkoPatchRequestDtoToFunko() {
        val result= funkoMapper.funkoPatchRequestDtoToFunko(funkoPatchRequestDto);
        assertAll(
                ()-> assertEquals(result.getName(),funko.getName()),
                ()->assertEquals(result.getCantidad(),funko.getCantidad()),
                ()-> assertEquals(result.getPrice(),funko.getPrice()),
                ()-> assertEquals(result.getImagen(),funko.getImagen()),
                ()-> assertEquals(result.getCategoria().getName(),funko.getCategoria().getName())
        );
    }
}