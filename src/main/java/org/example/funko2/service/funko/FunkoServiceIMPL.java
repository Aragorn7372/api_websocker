package org.example.funko2.service.funko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.example.funko2.config.webSocket.WebSocketConfig;
import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.exceptions.categoria.CategoriaNotFoundException;
import org.example.funko2.exceptions.funko.FunkoNotFoundException;
import org.example.funko2.handler.WebSocketHandler;
import org.example.funko2.mapper.FunkoMapper;
import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.example.funko2.repository.CategoriaRepository;
import org.example.funko2.repository.FunkoRepository;

import org.example.funko2.webSocket.dto.FunkoDtoWebSocketResponse;
import org.example.funko2.webSocket.mapper.NotificationMapper;
import org.example.funko2.webSocket.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@CacheConfig(cacheNames = {"FunkoResponses"})
public class FunkoServiceIMPL implements FunkoService {
    private final ObjectMapper jsonMapper;
    private final Logger logger = LoggerFactory.getLogger(FunkoServiceIMPL.class);
    private final FunkoRepository repository;
    private final CategoriaRepository categoria;
    private final FunkoMapper mapper;
    private WebSocketHandler webSocketService;
    private final WebSocketConfig webSocketConfig;

    @Autowired
    public FunkoServiceIMPL(FunkoRepository funkoRepositoryIMPL, FunkoMapper funkoMapper, CategoriaRepository categoriaRepository, WebSocketConfig webSocketConfig) {
        logger.debug("FunkoServiceIMPL created");
        this.repository = funkoRepositoryIMPL;
        this.mapper = funkoMapper;
        this.categoria = categoriaRepository;
        jsonMapper = new ObjectMapper();
        this.webSocketConfig = webSocketConfig;
        webSocketService = webSocketConfig.webSocketFunkosHandler();
    }

    @Override
    public List<FunkoResponseDto> findAll() {
        logger.debug("FunkoServiceIMPL findAll");
        return repository.findAll().stream().map(mapper::funkoToFunkoResponseDto).toList();
    }

    @Override
    @Cacheable(key = "#id")
    public FunkoResponseDto findById(Long id) {
        logger.debug("FunkoServiceIMPL findById");
        return mapper.funkoToFunkoResponseDto(repository.findById(id).orElseThrow(()->{
            val error= "funko id:"+ id+ " not found";
            logger.warn(error);
            return new FunkoNotFoundException(error);
            })
        );
    }

    @Override
    public FunkoResponseDto save(FunkoRequestDto funko) {
        logger.debug("FunkoServiceIMPL save");
        val categoriaEntity=categoria.findByNameIgnoreCase(funko.getCategoria()).orElseThrow(()->{
            logger.warn("no se encontro la categoria: "+funko.getCategoria());
            return new CategoriaNotFoundException("no se encontro la categoria: "+funko.getCategoria());
        });
        val funkoACrear=mapper.funkoRequestDtoToFunko(funko);
        funkoACrear.setCategoria(categoriaEntity);
        val funkoCreado=repository.save(funkoACrear);
        onChange(Notification.Tipo.CREATE, funkoCreado);
        return mapper.funkoToFunkoResponseDto(funkoCreado);
    }

    @Override
    @CacheEvict(key = "#result.id")
    public FunkoResponseDto patch(FunkoPatchRequestDto funko,Long id) {
        var categoriaEntity= Categoria.builder().build();
        if (funko.getCategoria()!=null){
            categoriaEntity=categoria.findByNameIgnoreCase(funko.getCategoria()).orElseThrow(()-> {
                logger.warn("no se encontro la categoria: " + funko.getCategoria());
                return new CategoriaNotFoundException("no se encontro la categoria: "+funko.getCategoria());
            });
        }

        logger.debug("FunkoServiceIMPL patch");
        Optional<Funko> funkoEncontrado=repository.findById(id);
        if(funkoEncontrado.isPresent()){
                if (funko.getName()!=null) {funkoEncontrado.get().setName(funko.getName());}
                if (funko.getCantidad()!=null) {funkoEncontrado.get().setCantidad(funko.getCantidad());}
                if (funko.getImagen()!=null) {funkoEncontrado.get().setImagen(funko.getImagen());}
                if (funko.getPrice()!=null) {funkoEncontrado.get().setPrice(funko.getPrice());}
                if (funko.getCategoria()!=null) {funkoEncontrado.get().setCategoria(categoriaEntity);}
            funkoEncontrado.get().setFechaModificacion(LocalDateTime.now());
                onChange(Notification.Tipo.UPDATE,funkoEncontrado.get());
            return mapper.funkoToFunkoResponseDto(repository.save(funkoEncontrado.get()));
        }else  {
            val error= "funko id:"+ id+ " not found";
            throw  new FunkoNotFoundException(error);
        }
    }

    @Override
    @CacheEvict(key = "#result.id")
    public FunkoResponseDto delete(Long id) {

        logger.debug("FunkoServiceIMPL delete");
        Optional<Funko> funkoEncontrado=repository.findById(id);

         if(funkoEncontrado.isPresent()){
             onChange(Notification.Tipo.DELETE, funkoEncontrado.get());
             repository.deleteById(id);}
         else {
            val error= "funko id:"+ id+ " not found";
            logger.warn(error);
            throw  new FunkoNotFoundException(error);
         }
         return mapper.funkoToFunkoResponseDto(funkoEncontrado.get());
    }

    @Override
    @CacheEvict(key = "#result.id")
    public FunkoResponseDto update(FunkoRequestDto funko, Long id) {
        val categoriaEntity = categoria.findByNameIgnoreCase(funko.getCategoria()).orElseThrow(()->{
            logger.warn("no se encontro la categoria: "+funko.getCategoria());
            return new CategoriaNotFoundException("no se encontro la categoria: "+funko.getCategoria());
        });
        logger.debug("FunkoServiceIMPL update");
        Optional<Funko> funkoEncontrado=repository.findById(id);
        if(funkoEncontrado.isPresent()){
            funkoEncontrado.get().setName(funko.getName());
            funkoEncontrado.get().setCantidad(funko.getCantidad());
            funkoEncontrado.get().setImagen(funko.getImagen());
            funkoEncontrado.get().setPrice(funko.getPrice());
            funkoEncontrado.get().setFechaModificacion(LocalDateTime.now());
            funkoEncontrado.get().setCategoria(categoriaEntity);
            onChange(Notification.Tipo.UPDATE,funkoEncontrado.get());
            return mapper.funkoToFunkoResponseDto(repository.save(funkoEncontrado.get()));
        }else  {
            val error= "funko id:"+ id+ " not found";
            throw  new FunkoNotFoundException(error);
        }
    }
    public List<FunkoResponseDto> findByName(String name) {
        logger.debug("FunkoServiceIMPL findByName");
        return repository.findFunkoByNameContainingIgnoreCase(name).stream().map(mapper::funkoToFunkoResponseDto).toList();
    }
    public FunkoResponseDto findByUuid(UUID uuid) {
        logger.debug("FunkoServiceIMPL findByUuid");
        return mapper.funkoToFunkoResponseDto(repository.findAllByUuid(uuid).orElseThrow(()->{
           logger.warn("no se encontro el funko: "+uuid);
           return new FunkoNotFoundException("no se encontro el funko: "+uuid.toString());
            })
        );
    }
    public List<FunkoResponseDto> findByCategory(String category) {
        logger.debug("FunkoServiceIMPL findByCategory");
        val categoriaFound=categoria.findByNameIgnoreCase(category).orElseThrow(()->{
            logger.warn("no se encontro la categoria: "+category);
            return new CategoriaNotFoundException("no se encontro la categoria: "+category);
        });
        return repository.findFunkoByCategoria(categoriaFound).stream().map(mapper::funkoToFunkoResponseDto).toList();
    }
    public List<FunkoResponseDto> findByPrecioMenor(Double precioMenor) {
        logger.debug("FunkoServiceIMPL findByPrecioMenor");
        return repository.findFunkoByPriceIsLessThan(precioMenor).stream().map(mapper::funkoToFunkoResponseDto).toList();
    }
    private void onChange(Notification.Tipo tipo, Funko data) {
        logger.debug("Servicio de productos onChange con tipo: " + tipo + " y datos: " + data);

        if (webSocketService == null) {
            logger.warn("No se ha podido enviar la notificación a los clientes ws, no se ha encontrado el servicio");
            webSocketService = this.webSocketConfig.webSocketFunkosHandler();
        }

        try {
            Notification<FunkoDtoWebSocketResponse> notificacion = new Notification<>(
                    "FUNKO",
                    tipo,
                    NotificationMapper.toFunkoDtoWebsocketResponse(data),
                    LocalDateTime.now().toString()
            );

            String json = jsonMapper.writeValueAsString((notificacion));

            logger.info("Enviando mensaje a los clientes ws");
            // Enviamos el mensaje a los clientes ws con un hilo, si hay muchos clientes, puede tardar
            // no bloqueamos el hilo principal que atiende las peticiones http
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
