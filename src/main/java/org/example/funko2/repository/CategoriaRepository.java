package org.example.funko2.repository;

import org.example.funko2.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
/**
 * Repositorio para la entidad Categoria.
 * @author Aragorn7372
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    /**
     * Busca una categoría por su nombre ignorando mayúsculas/minúsculas.
     * @param name Nombre de la categoría.
     * @return Optional con la categoría si se encuentra.
     */
    Optional<Categoria> findByNameIgnoreCase(String name);
}
