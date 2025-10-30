package org.example.funko2.service.funko;

import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.service.Service;

import java.util.List;
import java.util.UUID;

public interface FunkoService extends Service<FunkoResponseDto,Long,FunkoRequestDto> {
    FunkoResponseDto findByUuid(UUID uuid);
    List<FunkoResponseDto> findByName(String name);
    List<FunkoResponseDto> findByCategory(String category);
    List<FunkoResponseDto> findByPrecioMenor(Double precioMenor);
    FunkoResponseDto patch(FunkoPatchRequestDto funko, Long id);
}
