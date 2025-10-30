package org.example.funko2.repository;

import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FunkoRepository extends JpaRepository<Funko, Long> {
    Optional<Funko> findAllByUuid(UUID uuid);
    List<Funko> findFunkoByNameContainingIgnoreCase(String funkoName);
    List<Funko> findFunkoByCategoria(Categoria categoria);
    List<Funko> findFunkoByPriceIsLessThan(Double price);
    @Query("SELECT f FROM Funko f WHERE f.uuid= ?1")
    Optional<Funko> getByUuid(UUID uuid);
    @Query("SELECT f FROM Funko f WHERE f.name LIKE LOWER(?1)")
    List<Funko> getByName(String name);
    @Query("SELECT f FROM Funko f WHERE f.categoria.name LIKE LOWER(?1)")
    List<Funko> getByCategoria(String categoria);
    @Query("SELECT f FROM  Funko f WHERE f.price< ?1")
    List<Funko> getByPriceLessThan(Double price);
}