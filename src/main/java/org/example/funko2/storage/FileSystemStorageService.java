package org.example.funko2.storage;

import lombok.extern.slf4j.Slf4j;
import org.example.funko2.exceptions.storage.StorageBadRequest;
import org.example.funko2.exceptions.storage.StorageInternal;
import org.example.funko2.exceptions.storage.StorageNotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.example.funko2.controller.StorageController;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
@Service
@Slf4j
public class FileSystemStorageService implements StorageService{

    // Directorio raiz de nuestro almacén de ficheros
    private final Path rootLocation;
    public FileSystemStorageService(@Value("${upload.root-location}") String path) {
        this.rootLocation = Paths.get(path);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageInternal("no se pudo crear el almacenamiento"+e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = StringUtils.getFilenameExtension(filename);
        String justFileName = filename.replace("."+extension, "");
        String storedFileName= UUID.randomUUID()+justFileName.replaceAll("\\s+", "")+"."+extension;
        try {
            if (file.isEmpty()) {
                throw new StorageBadRequest("No se encuentra un archivo con esa ruta relativa" +filename);
            }
            if(filename.contains("..")){
                throw new StorageBadRequest("No se puede almacenar un fichero con una ruta fuera del directorio original"+filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                log.info("Almacenando fichero {} como {}", filename, storedFileName);
                Files.copy(inputStream, this.rootLocation.resolve(storedFileName),
                        StandardCopyOption.REPLACE_EXISTING);
                return storedFileName;
            }
        }catch (IOException ex){
            throw new StorageInternal("fallo al almacenar el fichero: "+ filename+" "+ex);
        }


    }

    @Override
    public Stream<Path> loadAll() {
        log.info("Iniciando lista de archivos");
        try{
            return Files.walk(this.rootLocation,1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException ex){
            throw new StorageInternal("fallo al iniciando lista de archivos"+ex );
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else  {
                throw new StorageNotFound("Fichero no encontrado: " + filename);
            }
        }catch (MalformedURLException ex){
            throw new StorageNotFound("no se puede leer fichero"+ filename+" "+ex);
        }
    }

    @Override
    public void delete(String filename) {
        String justFileName = StringUtils.getFilename(filename);
        try {
            log.info("Eliminando {}", filename);
            Path file = load(justFileName);
            Files.deleteIfExists(file);
        }catch (IOException ex){
            throw new StorageInternal("fallo al eliminar el fichero"+ filename+" "+ex);
        }
    }

    @Override
    public void deleteAll() {
        log.info("Eliminando lista de archivos");
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public String getUrl(String filename) {
        return MvcUriComponentsBuilder
                .fromMethodName(StorageController.class,"serveFile",filename,null)
                .build().toUriString();
    }
}
