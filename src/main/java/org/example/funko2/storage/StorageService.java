package org.example.funko2.storage;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Interfaz que define los métodos para el almacenamiento de archivos.
 * @author Aragorn7372
 */ 
public interface StorageService {

    /**
     * Inicializa el almacenamiento.
     */
    void init();

    /**
     * Almacena un archivo.
     * @param file Archivo a almacenar.
     * @return Nombre del archivo almacenado.
     */
    String store(MultipartFile file);

    /**
     * Obtiene todos los archivos almacenados.
     * @return Stream de Path de los archivos almacenados.
     */
    Stream<Path> loadAll();

    /**
     * Obtiene un archivo almacenado.
     * @param filename Nombre del archivo.
     * @return Path del archivo.
     */
    Path load(String filename);

    /**
     * Obtiene un archivo almacenado como Resource.
     * @param filename Nombre del archivo.
     * @return Resource del archivo.
     */
    Resource loadAsResource(String filename);

    /**
     * Elimina un archivo almacenado.
     * @param filename Nombre del archivo.
     */
    void delete(String filename);

    /**
     * Elimina todos los archivos almacenados.
     */
    void deleteAll();

    /**
     * Obtiene la URL de un archivo almacenado.
     * @param filename Nombre del archivo.
     * @return URL del archivo.
     */
    String getUrl(String filename);

}
