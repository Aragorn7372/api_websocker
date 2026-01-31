package org.example.funko2.config.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración del almacenamiento.
 * @author Aragorn7372
 */
@Configuration
@Slf4j
public class StorageConfig {
    /**
     * Inicializa el almacenamiento.
     * @param storageService Servicio de almacenamiento.
     * @param deleteAll Indica si se deben borrar todos los ficheros.
     * @return Inicializa el almacenamiento.
     */
    @Bean
    public CommandLineRunner init(StorageService storageService, @Value("${upload.delete}") String deleteAll) {
        return args -> {
            // Inicializamos el servicio de ficheros
            // Leemos de application.properties si necesitamos borrar todo o no

            if (deleteAll.equals("true")) {
                log.info("Borrando ficheros de almacenamiento...");
                storageService.deleteAll();
            }

            storageService.init(); // inicializamos
        };
    }
}
