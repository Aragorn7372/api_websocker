package org.example.funko2.mapper;

import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FunkoMapper {

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



