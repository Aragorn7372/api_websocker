package org.example.funko2.service.funko;

import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.service.Service;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para la gestión de Funkos.
 * Extiende de la interfaz genérica Service.
 * @author Aragorn7372
 */
public interface FunkoService extends Service<FunkoResponseDto,Long,FunkoRequestDto> {
    /**
     * Busca un Funko por su UUID.
     * @param uuid UUID del Funko.
     * @return FunkoResponseDto encontrado.
     */
    FunkoResponseDto findByUuid(UUID uuid);

    /**
     * Busca Funkos por nombre.
     * @param name Nombre o parte del nombre.
     * @return Lista de Funkos coincidentes.
     */
    List<FunkoResponseDto> findByName(String name);

    /**
     * Busca Funkos por categoría.
     * @param category Nombre de la categoría.
     * @return Lista de Funkos de la categoría.
     */
    List<FunkoResponseDto> findByCategory(String category);

    /**
     * Busca Funkos con precio menor al indicado.
     * @param precioMenor Precio máximo.
     * @return Lista de Funkos con precio menor.
     */
    List<FunkoResponseDto> findByPrecioMenor(Double precioMenor);

    /**
     * Actualiza parcialmente un Funko.
     * @param funko DTO con los datos a actualizar.
     * @param id ID del Funko.
     * @return FunkoResponseDto actualizado.
     */
    FunkoResponseDto patch(FunkoPatchRequestDto funko, Long id);
}
