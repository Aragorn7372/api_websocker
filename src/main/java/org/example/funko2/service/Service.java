package org.example.funko2.service;

import java.util.List;

/**
 * Interfaz genérica para los servicios.
 * @param <T> Tipo del DTO de respuesta.
 * @param <ID> Tipo del identificador.
 * @param <R> Tipo del DTO de petición.
 * @author Aragorn7372
 */
public interface Service<T,ID,R> {
    /**
     * Obtiene todos los elementos.
     * @return Lista de elementos.
     */
    List<T> findAll();

    /**
     * Obtiene un elemento por su identificador.
     * @param id Identificador del elemento.
     * @return Elemento encontrado.
     */
    T findById(ID id);

    /**
     * Guarda un nuevo elemento.
     * @param funko DTO con los datos del elemento a guardar.
     * @return Elemento guardado.
     */
    T save(R funko);

    /**
     * Elimina un elemento por su identificador.
     * @param id Identificador del elemento a eliminar.
     * @return Elemento eliminado.
     */
    T delete(ID id);

    /**
     * Actualiza un elemento existente.
     * @param funko DTO con los nuevos datos.
     * @param id Identificador del elemento a actualizar.
     * @return Elemento actualizado.
     */
    T update(R funko, ID id);
}
