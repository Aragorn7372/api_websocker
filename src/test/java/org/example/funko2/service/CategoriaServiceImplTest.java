package org.example.funko2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.example.funko2.config.webSocket.WebSocketConfig;
import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.exceptions.categoria.CategoriaNotFoundException;
import org.example.funko2.handler.WebSocketHandler;
import org.example.funko2.mapper.CategoriaMapper;
import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.example.funko2.repository.CategoriaRepository;
import org.example.funko2.repository.FunkoRepository;
import org.example.funko2.service.categoria.CategoriaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {
    /*
    private final FunkoRepository funkoRepository;
    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);
    private WebSocketHandler webSocketService;
    private final WebSocketConfig webSocketConfig;
    private final ObjectMapper jsonMapper
     */
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private FunkoRepository funkoRepository;
    @Mock
    private WebSocketHandler webSocketHandler;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private WebSocketConfig webSocketConfig;
    @Mock
    private CategoriaMapper categoriaMapper;
    @InjectMocks
    private CategoriaServiceImpl categoriaServiceImpl;
    private final Categoria categoria= Categoria
            .builder()
            .id(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .name("ANIME")
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .build();
    private final CategoriaResponseDto categoriaResponseDto= CategoriaResponseDto.builder().id(categoria.getId()).name(categoria.getName()).build();
    private final CategoriaRequestDto categoriaRequestDto= CategoriaRequestDto.builder().name(categoria.getName()).build();
    private final Funko funko= Funko.builder()
            .id(1L)
            .name("hola")
            .price(1.0)
            .imagen("imagen.png")
            .cantidad(3)
            .uuid(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .categoria(categoria)
            .build();
    @Nested
    @DisplayName("test buenos")
    class buenos{
        @Test
        void findAll() {
            when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
            when(categoriaMapper.categoriaToResponseDto(categoria)).thenReturn(categoriaResponseDto);
            val result = categoriaServiceImpl.findAll();
            assertEquals(categoriaResponseDto, result.getFirst());
            verify(categoriaRepository,times(1)).findAll();
            verify(categoriaMapper,times(1)).categoriaToResponseDto(categoria);
        }

        @Test
        void findById() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
            when(categoriaMapper.categoriaToResponseDto(categoria)).thenReturn(categoriaResponseDto);
            val result = categoriaServiceImpl.findById(categoria.getId());
            assertEquals(categoriaResponseDto, result);
            verify(categoriaRepository,times(1)).findById(categoria.getId());
            verify(categoriaMapper,times(1)).categoriaToResponseDto(categoria);

        }

        @Test
        void save() {
            when(categoriaRepository.save(categoria)).thenReturn(categoria);
            when(categoriaMapper.categoriaToResponseDto(categoria)).thenReturn(categoriaResponseDto);
            when(categoriaMapper.categoriaRequestDtoToCategoria(categoriaRequestDto)).thenReturn(categoria);
            val result= categoriaServiceImpl.save(categoriaRequestDto);
            assertEquals(categoriaResponseDto, result);
            verify(categoriaRepository,times(1)).save(categoria);
            verify(categoriaMapper,times(1)).categoriaToResponseDto(categoria);
            verify(categoriaMapper,times(1)).categoriaRequestDtoToCategoria(categoriaRequestDto);
        }

        @Test
        void delete() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
            when(funkoRepository.findFunkoByCategoria(categoria)).thenReturn(List.of());
            when(categoriaMapper.categoriaToResponseDto(categoria)).thenReturn(categoriaResponseDto);
            val result= categoriaServiceImpl.delete(categoria.getId());
            assertEquals(categoriaResponseDto, result);
            verify(categoriaRepository,times(1)).findById(categoria.getId());
            verify(categoriaMapper,times(1)).categoriaToResponseDto(categoria);
            verify(categoriaRepository,times(1)).deleteById(categoria.getId());
        }

        @Test
        void update() {

            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
            when(categoriaRepository.findByNameIgnoreCase(categoria.getName())).thenReturn(Optional.of(categoria));
            when(categoriaRepository.save(categoria)).thenReturn(categoria);
            when(categoriaMapper.categoriaToResponseDto(categoria)).thenReturn(categoriaResponseDto);
            val result= categoriaServiceImpl.update(categoriaRequestDto,categoria.getId());
            assertEquals(categoriaResponseDto, result);
            verify(categoriaRepository,times(1)).findByNameIgnoreCase(categoria.getName());
            verify(categoriaMapper,times(1)).categoriaToResponseDto(categoria);
            verify(categoriaRepository,times(1)).save(categoria);
            verify(categoriaRepository,times(1)).findById(categoria.getId());

        }
        @Test
        @DisplayName("update no category found by name")
        void updateNoCategoryFoundByName() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
            when(categoriaRepository.findByNameIgnoreCase(categoria.getName())).thenReturn(Optional.empty());
            when(categoriaRepository.save(categoria)).thenReturn(categoria);
            when(categoriaMapper.categoriaToResponseDto(categoria)).thenReturn(categoriaResponseDto);
            val result= categoriaServiceImpl.update(categoriaRequestDto,categoria.getId());
            assertEquals(categoriaResponseDto, result);
            verify(categoriaRepository,times(1)).findByNameIgnoreCase(categoria.getName());
            verify(categoriaMapper,times(1)).categoriaToResponseDto(categoria);
            verify(categoriaRepository,times(1)).save(categoria);
            verify(categoriaRepository,times(1)).findById(categoria.getId());
        }
    }
    @Nested
    @DisplayName("test malos")
    class malos{
        @Test
        @DisplayName("find by id not found")
        void findByIdNotFound() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.empty());
            val result=assertThrows(CategoriaNotFoundException.class, ()-> categoriaServiceImpl.findById(categoria.getId()));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"categoria no encontrada con uuid: 4b23bd64-c198-4eda-9d84-d4bdb0e5a24f","deberian ser iguales")
            );
            verify(categoriaRepository,times(1)).findById(categoria.getId());
            verify(categoriaMapper,times(0)).categoriaToResponseDto(categoria);

        }
        @Test
        @DisplayName("update not found")
        void updateNotFound() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.empty());
            val result=assertThrows(CategoriaNotFoundException.class, ()-> categoriaServiceImpl.update(categoriaRequestDto,categoria.getId()));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"categoria no encontrada con uuid: 4b23bd64-c198-4eda-9d84-d4bdb0e5a24f","deberian ser iguales")
            );
            verify(categoriaRepository,times(0)).findByNameIgnoreCase(categoria.getName());
            verify(categoriaMapper,times(0)).categoriaToResponseDto(categoria);
            verify(categoriaRepository,times(0)).save(categoria);
            verify(categoriaRepository,times(1)).findById(categoria.getId());
        }
        @Test
        @DisplayName("delete not found")
        void deleteNotFound() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.empty());
            val result=assertThrows(CategoriaNotFoundException.class, ()-> categoriaServiceImpl.delete(categoria.getId()));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"categoria no encontrada con uuid: 4b23bd64-c198-4eda-9d84-d4bdb0e5a24f","deberian ser iguales")
            );
            verify(categoriaRepository,times(0)).findByNameIgnoreCase(categoria.getName());
            verify(categoriaRepository,times(0)).deleteById(categoria.getId());
            verify(categoriaRepository,times(1)).findById(categoria.getId());
        }
        @Test
        @DisplayName("delete bad funkos with category")
        void deleteBadFunkosWithCategory() {
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
            when(funkoRepository.findFunkoByCategoria(categoria)).thenReturn(List.of(funko));
            val result=assertThrows(DataIntegrityViolationException.class, ()-> categoriaServiceImpl.delete(categoria.getId()));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"no puedes eliminar una categoria que distintos funkos estan actualmente usando","deberian ser iguales")
            );
            verify(categoriaMapper,times(0)).categoriaToResponseDto(categoria);
            verify(categoriaRepository,times(0)).deleteById(categoria.getId());
            verify(categoriaRepository,times(1)).findById(categoria.getId());
        }
        @Test
        @DisplayName("update bad other categoria have that name")
        void updateBadOtherCategoriaHasThatName() {
            Categoria categoria2= Categoria.builder().id(UUID.randomUUID()).name(categoria.getName()).fechaCreacion(categoria.getFechaCreacion()).fechaModificacion(categoria.getFechaModificacion()).build();
            when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
            when(categoriaRepository.findByNameIgnoreCase(categoria.getName())).thenReturn(Optional.of(categoria2));
            val result=assertThrows(DataIntegrityViolationException.class, ()-> categoriaServiceImpl.update(categoriaRequestDto,categoria.getId()));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"ya existe una categoria con ese nombre","deberian ser iguales")
            );
            verify(categoriaRepository,times(1)).findByNameIgnoreCase(categoria.getName());
            verify(categoriaMapper,times(0)).categoriaToResponseDto(categoria);
            verify(categoriaRepository,times(0)).save(categoria);
            verify(categoriaRepository,times(1)).findById(categoria.getId());

        }
    }
}