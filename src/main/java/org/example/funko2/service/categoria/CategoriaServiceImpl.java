package org.example.funko2.service.categoria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.example.funko2.config.webSocket.WebSocketConfig;
import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.exceptions.categoria.CategoriaNotFoundException;
import org.example.funko2.handler.WebSocketHandler;
import org.example.funko2.mapper.CategoriaMapper;
import org.example.funko2.model.Categoria;
import org.example.funko2.repository.CategoriaRepository;
import org.example.funko2.repository.FunkoRepository;
import org.example.funko2.webSocket.dto.CategoriaDtoWebSocketResponse;
import org.example.funko2.webSocket.mapper.NotificationMapper;
import org.example.funko2.webSocket.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    private final FunkoRepository funkoRepository;
    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);
    private WebSocketHandler webSocketService;
    private final WebSocketConfig webSocketConfig;
    private final ObjectMapper jsonMapper;
    @Autowired
    public CategoriaServiceImpl(FunkoRepository funkoRepository, CategoriaRepository repository, CategoriaMapper mapper, WebSocketConfig webSocketConfig) {
        this.funkoRepository = funkoRepository;
        this.repository = repository;
        this.mapper = mapper;
       this.jsonMapper = new ObjectMapper();
       this.webSocketConfig=webSocketConfig;
        webSocketService = webSocketConfig.webSocketFunkosHandler();

    }
    String error="categoria no encontrada con uuid: ";
    @Override
    public List<CategoriaResponseDto> findAll() {
        logger.info("Iniciando lista de categorias");
        return repository.findAll().stream().map(mapper::categoriaToResponseDto).toList();
    }

    @Override
    public CategoriaResponseDto findById(UUID uuid) {
        return mapper.categoriaToResponseDto(repository.findById(uuid).orElseThrow(()->{
            logger.warn("FunkoServiceIMPL findById no encontrado");
            return new CategoriaNotFoundException(error+uuid);
            })
        );
    }

    @Override
    public CategoriaResponseDto save(CategoriaRequestDto categoria) {
        val categoriaSave=repository.save(mapper.categoriaRequestDtoToCategoria(categoria));
        onChange(Notification.Tipo.CREATE, categoriaSave);
        return mapper.categoriaToResponseDto(categoriaSave);
    }

    @Override
    public CategoriaResponseDto delete(UUID uuid) {
        val categoria = repository.findById(uuid).orElseThrow(() ->{
            logger.warn("no se puede eliminar una categoria no guardada");
            return new CategoriaNotFoundException(error+uuid);
        });
        if (!funkoRepository.findFunkoByCategoria(categoria).isEmpty()) {
            logger.warn("no puedes eliminar una categoria que distintos funkos estan actualmente usando");
            throw  new DataIntegrityViolationException("no puedes eliminar una categoria que distintos funkos estan actualmente usando");
        }

        repository.deleteById(uuid);
        onChange(Notification.Tipo.DELETE,categoria);
        return mapper.categoriaToResponseDto(categoria);
    }

    @Override
    public CategoriaResponseDto update(CategoriaRequestDto categoriaRequestDto, UUID uuid) {
        val categoria= repository.findById(uuid).orElseThrow(()-> {
            logger.warn("no se encontro categoria con uuid: {}", uuid);
            return new CategoriaNotFoundException(error+uuid);
        });
        val categoriaNombre= repository.findByNameIgnoreCase(categoriaRequestDto.getName());
        if (!categoriaNombre.isPresent() || categoriaNombre.get().getId().equals(uuid)) {
            categoria.setName(categoriaRequestDto.getName());
            repository.save(categoria);
        } else {
            logger.warn("ya existe una categoria con ese nombre");
            throw new DataIntegrityViolationException("ya existe una categoria con ese nombre");
        }
        onChange(Notification.Tipo.UPDATE, categoria);
        return mapper.categoriaToResponseDto(categoria);
    }
    private void onChange(Notification.Tipo tipo, Categoria data) {
        logger.debug("Servicio de productos onChange con tipo: " + tipo + " y datos: " + data);

        if (webSocketService == null) {
            logger.warn("No se ha podido enviar la notificación a los clientes ws, no se ha encontrado el servicio");
            webSocketService = this.webSocketConfig.webSocketCategoriasHandler();
        }

        try {
            Notification<CategoriaDtoWebSocketResponse> notificacion = new Notification<>(
                    "CATEGORIA",
                    tipo,
                    NotificationMapper.toCategoriaDtoWebSocketResponse(data),
                    LocalDateTime.now().toString()
            );

            String json = jsonMapper.writeValueAsString((notificacion));

            logger.info("Enviando mensaje a los clientes ws");
            Thread senderThread = new Thread(() -> {
                try {
                    webSocketService.sendMessage(json);
                } catch (Exception e) {
                    logger.error("Error al enviar el mensaje a través del servicio WebSocket", e);
                }
            });
            senderThread.start();
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir la notificación a JSON", e);
        }
    }

}
