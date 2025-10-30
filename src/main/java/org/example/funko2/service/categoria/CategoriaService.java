package org.example.funko2.service.categoria;

import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.service.Service;

import java.util.UUID;

public interface CategoriaService extends Service<CategoriaResponseDto, UUID, CategoriaRequestDto> {
}
