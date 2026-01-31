package org.example.funko2.mapper;

import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapper para la conversión entre DTOs y la entidad Funko.
 * @author Aragorn7372
 */
@Component
public class FunkoMapper {

    /**
     * Convierte un FunkoRequestDto a una entidad Funko.
     * @param funkoRequestDto DTO de petición.
     * @return Entidad Funko.
     */
    public Funko funkoRequestDtoToFunko(FunkoRequestDto funkoRequestDto) {
        return Funko.builder()
                .uuid(UUID.randomUUID())
                .name(funkoRequestDto.getName())
                .price(funkoRequestDto.getPrice())
                .cantidad(funkoRequestDto.getCantidad())
                .imagen(funkoRequestDto.getImagen())
                .categoria(Categoria.builder().name(funkoRequestDto.getCategoria()).build())
                .build();
    }
    /**
     * Convierte una entidad Funko a FunkoResponseDto.
     * @param funko Entidad Funko.
     * @return FunkoResponseDto.
     */
    public FunkoResponseDto funkoToFunkoResponseDto(Funko funko){
        return FunkoResponseDto.builder()
                .id(funko.getId())
                .uuid(funko.getUuid())
                .name(funko.getName())
                .price(funko.getPrice())
                .cantidad(funko.getCantidad())
                .imagen(funko.getImagen())
                .categoria(funko.getCategoria().getName())
                .build();
    }
    /**
     * Convierte un FunkoPatchRequestDto a una entidad Funko (parcial).
     * @param funkoRequestDto DTO de petición PATCH.
     * @return Entidad Funko.
     */
    public Funko funkoPatchRequestDtoToFunko(FunkoPatchRequestDto funkoRequestDto) {
        return Funko.builder()
                .name(funkoRequestDto.getName())
                .price(funkoRequestDto.getPrice())
                .cantidad(funkoRequestDto.getCantidad())
                .imagen(funkoRequestDto.getImagen())
                .categoria(Categoria.builder().name(funkoRequestDto.getCategoria()).build())
                .build();
    }
}



