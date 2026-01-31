package org.example.funko2.repository;

import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la entidad Funko.
 * @author Aragorn7372
 */
@Repository
public interface FunkoRepository extends JpaRepository<Funko, Long> {
    /**
     * Busca un Funko por su UUID.
     * @param uuid UUID del Funko.
     * @return Optional con el Funko si se encuentra.
     */
    Optional<Funko> findAllByUuid(UUID uuid);

    /**
     * Busca Funkos cuyo nombre contenga el texto proporcionado (ignorando caso).
     * @param funkoName Texto a buscar en el nombre.
     * @return Lista de Funkos coincidentes.
     */
    List<Funko> findFunkoByNameContainingIgnoreCase(String funkoName);

    /**
     * Busca Funkos por categoría.
     * @param categoria Categoría a filtrar.
     * @return Lista de Funkos de la categoría.
     */
    List<Funko> findFunkoByCategoria(Categoria categoria);

    /**
     * Busca Funkos con precio menor al indicado.
     * @param price Precio máximo.
     * @return Lista de Funkos con precio menor.
     */
    List<Funko> findFunkoByPriceIsLessThan(Double price);

    /**
     * Consulta personalizada para obtener un Funko por UUID.
     * @param uuid UUID del Funko.
     * @return Optional con el Funko si se encuentra.
     */
    @Query("SELECT f FROM Funko f WHERE f.uuid= ?1")
    Optional<Funko> getByUuid(UUID uuid);

    /**
     * Consulta personalizada para obtener Funkos por nombre (búsqueda parcial).
     * @param name Patrón de nombre.
     * @return Lista de Funkos coincidentes.
     */
    @Query("SELECT f FROM Funko f WHERE f.name LIKE LOWER(?1)")
    List<Funko> getByName(String name);

    /**
     * Consulta personalizada para obtener Funkos por nombre de categoría (búsqueda parcial).
     * @param categoria Patrón de nombre de categoría.
     * @return Lista de Funkos coincidentes.
     */
    @Query("SELECT f FROM Funko f WHERE f.categoria.name LIKE LOWER(?1)")
    List<Funko> getByCategoria(String categoria);

    /**
     * Consulta personalizada para obtener Funkos con precio menor al indicado.
     * @param price Precio límite.
     * @return Lista de Funkos con precio menor.
     */
    @Query("SELECT f FROM  Funko f WHERE f.price< ?1")
    List<Funko> getByPriceLessThan(Double price);
}