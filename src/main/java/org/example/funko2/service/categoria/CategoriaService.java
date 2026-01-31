package org.example.funko2.service.categoria;

import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.service.Service;

import java.util.UUID;

/**
 * Servicio para la gestión de categorías.
 * Extiende de la interfaz genérica Service.
 * @author Aragorn7372
 */
public interface CategoriaService extends Service<CategoriaResponseDto, UUID, CategoriaRequestDto> {
}
