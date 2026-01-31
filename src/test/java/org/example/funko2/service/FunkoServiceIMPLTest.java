package org.example.funko2.service;

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
import org.example.funko2.service.funko.FunkoServiceIMPL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunkoServiceIMPLTest {
    @Mock
    private FunkoRepository repository;
    @Mock
    private FunkoMapper mapper;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private WebSocketHandler webSocketHandler;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private WebSocketConfig webSocketConfig;
    @InjectMocks
    private FunkoServiceIMPL service;
    private final Categoria categoria= Categoria
            .builder()
            .id(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .name("ANIME")
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .build();
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
    private final FunkoResponseDto funkoResponse=FunkoResponseDto.builder()
            .id(funko.getId())
            .name(funko.getName())
            .price(funko.getPrice())
            .imagen(funko.getImagen())
            .cantidad(funko.getCantidad())
            .uuid(funko.getUuid())
            .categoria("ANIME").build();
    private final FunkoRequestDto funkoRequestDto= FunkoRequestDto.builder()
            .name(funko.getName())
            .price(funko.getPrice())
            .imagen(funko.getImagen())
            .categoria("ANIME")
            .cantidad(funko.getCantidad())
            .build();
    private final FunkoPatchRequestDto funkoPatchRequestDto= FunkoPatchRequestDto.builder()
            .name(funko.getName())
            .price(funko.getPrice())
            .imagen(funko.getImagen())
            .categoria("ANIME")
            .cantidad(funko.getCantidad())
            .build();
    @Nested
    @DisplayName("good test")
    class TestServicioBuenos {
        @Test
        void findAll() {
            when(repository.findAll()).thenReturn(List.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            List<FunkoResponseDto> result = service.findAll();
            assertAll(
                    () -> assertTrue(result.size() == 1, "deberia contener un elemento"),
                    () -> assertEquals(result.getFirst(), funkoResponse, "deberia ser el mismo")
            );
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).findAll();
        }
        @Test
        @DisplayName("encontrar por nombre")
        void findByName() {
            when(repository.findFunkoByNameContainingIgnoreCase(funko.getName())).thenReturn(List.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            List<FunkoResponseDto> result = service.findByName(funko.getName());
            assertAll(
                    () -> assertTrue(result.size() == 1, "deberia contener un elemento"),
                    () -> assertEquals(result.getFirst(), funkoResponse, "deberia ser el mismo")
            );
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).findFunkoByNameContainingIgnoreCase(funko.getName());

        }
        @Test
        @DisplayName("encontrar bien por uuid")
        void findByUuid() {
            when(repository.findAllByUuid(funko.getUuid())).thenReturn(Optional.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            FunkoResponseDto result = service.findByUuid(funko.getUuid());
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).findAllByUuid(funko.getUuid());
        }
        @Test
        @DisplayName("encontrar lista por categoria")
        void findByCategoria() {
            when(repository.findFunkoByCategoria(funko.getCategoria())).thenReturn(List.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.of(funko.getCategoria()));
            List<FunkoResponseDto> result= service.findByCategory(funko.getCategoria().getName());
            assertAll(
                    () -> assertTrue(result.size() == 1, "deberia contener un elemento"),
                    () -> assertEquals(result.getFirst(), funkoResponse, "deberia ser el mismo")
            );
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).findFunkoByCategoria(funko.getCategoria());
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
        }
        @Test
        @DisplayName("encontrar funko precio menor a")
        void findByPrecioMenorA() {
            when(repository.findFunkoByPriceIsLessThan(funko.getPrice()+1)).thenReturn(List.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            List<FunkoResponseDto> result= service.findByPrecioMenor(funko.getPrice()+1);
            assertAll(
                    () -> assertTrue(result.size() == 1, "deberia contener un elemento"),
                    () -> assertEquals(result.getFirst(), funkoResponse, "deberia ser el mismo")
            );
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).findFunkoByPriceIsLessThan(funko.getPrice()+1);
        }
        @Test
        @DisplayName("encontrar bien")
        void findById() {
            when(repository.findById(1L)).thenReturn(Optional.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            FunkoResponseDto result = service.findById(1L);
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("guardar bien")
        void save() {
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.of(categoria));
            when(repository.save(funko)).thenReturn(funko);
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            when(mapper.funkoRequestDtoToFunko(funkoRequestDto)).thenReturn(funko);
            FunkoResponseDto result = service.save(funkoRequestDto);
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).save(funko);

        }

        @Test
        @DisplayName("patch good")
        void patch() {
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.of(categoria));
            when(repository.save(funko)).thenReturn(funko);
            when(repository.findById(1L)).thenReturn(Optional.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            FunkoResponseDto result = service.patch(funkoPatchRequestDto,1L);
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).save(funko);
            verify(repository, times(1)).findById(1L);
        }
        @Test
        @DisplayName("patch with no parameter")
        void patchWithNoParameter() {
            when(repository.save(funko)).thenReturn(funko);
            when(repository.findById(1L)).thenReturn(Optional.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            FunkoResponseDto result = service.patch(FunkoPatchRequestDto.builder().build(),1L);
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );
            verify(categoriaRepository, times(0)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).save(funko);
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("delete good")
        void delete() {
            when(repository.findById(1L)).thenReturn(Optional.of(funko));
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            FunkoResponseDto result = service.delete(1L);
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );

            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).deleteById(1L);
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("update good")
        void updateFunko() {
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.of(categoria));
            when(repository.findById(1L)).thenReturn(Optional.of(funko));
            when(repository.save(funko)).thenReturn(funko);
            when(mapper.funkoToFunkoResponseDto(funko)).thenReturn(funkoResponse);
            FunkoResponseDto result = service.update(funkoRequestDto,1L);
            assertAll(
                    ()-> assertEquals(result,funkoResponse,"deberian ser iguales")
            );
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper, times(1)).funkoToFunkoResponseDto(funko);
            verify(repository, times(1)).save(funko);
            verify(repository, times(1)).findById(1L);

        }
    }
    @Nested
    @DisplayName("test bad")
    class BadTest{
        @Test
        @DisplayName("encontrar por uuid not fount")
        void findByUuidBad(){

            when(repository.findAllByUuid(funko.getUuid())).thenReturn(Optional.empty());
            val result=assertThrows(FunkoNotFoundException.class, () -> service.findByUuid(funko.getUuid()));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"no se encontro el funko: 4b23bd64-c198-4eda-9d84-d4bdb0e5a24f","deberian ser iguales")
            );
                verify(mapper, times(0)).funkoToFunkoResponseDto(funko);
                verify(repository, times(1)).findAllByUuid(funko.getUuid());

        }
        @Test
        @DisplayName("find by categoria not found")
        void findByCategoriaBad(){
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.empty());
            val result = assertThrows(CategoriaNotFoundException.class, () -> service.findByCategory(funko.getCategoria().getName()));
            assertEquals("no se encontro la categoria: ANIME", result.getMessage(), "deberian ser iguales");
            verify(mapper, times(0)).funkoToFunkoResponseDto(funko);
            verify(repository, times(0)).findFunkoByCategoria(funko.getCategoria());
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
        }
        @Test
        @DisplayName("categoria not found save")
        void categoriaNotFound() {
            when(categoriaRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
            val result = assertThrows(CategoriaNotFoundException.class, () -> service.save(funkoRequestDto));
            assertEquals("no se encontro la categoria: ANIME", result.getMessage(), "deberian ser iguales");
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funkoRequestDto.getCategoria());
            verify(repository, times(0)).save(funko);
            verify(mapper, times(0)).funkoToFunkoResponseDto(funko);
            verify(mapper,times(0)).funkoRequestDtoToFunko(funkoRequestDto);
        }
        @Test
        @DisplayName("update bad categoria not found")
        void updateBadCategoria() {
            when(categoriaRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
            val result = assertThrows(CategoriaNotFoundException.class, () -> service.update(funkoRequestDto,1L));
            assertEquals("no se encontro la categoria: ANIME", result.getMessage(), "deberian ser iguales");
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper, times(0)).funkoToFunkoResponseDto(funko);
            verify(repository, times(0)).save(funko);
            verify(repository, times(0)).findById(1L);
        }
        @Test
        @DisplayName("patch bad categoria not found")
        void patchBadCategoria() {
            when(categoriaRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
            val result = assertThrows(CategoriaNotFoundException.class, () -> service.patch(funkoPatchRequestDto,1L));
            assertEquals("no se encontro la categoria: ANIME", result.getMessage(), "deberian ser iguales");
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper, times(0)).funkoToFunkoResponseDto(funko);
            verify(repository, times(0)).save(funko);
            verify(repository, times(0)).findById(1L);
        }
        @Test
        @DisplayName("find by id Bad")
        void findById() {
            when(repository.findById(1L)).thenReturn(Optional.empty());
            val result=assertThrows(FunkoNotFoundException.class, () -> service.findById(1L));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"funko id:1 not found","deberian ser iguales")
            );
            verify(mapper, times(0)).funkoToFunkoResponseDto(funko);
            verify(repository,times(1)).findById(1L);
        }
        @Test
        @DisplayName("update by id Bad")
        void updateByIdBad() {
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.of(categoria));
            when(repository.findById(1L)).thenReturn(Optional.empty());
            val result=assertThrows(FunkoNotFoundException.class, ()-> service.update(funkoRequestDto,1L));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"funko id:1 not found","deberian ser iguales")
            );
            verify(repository,times(1)).findById(1L);
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(repository,times(0)).save(funko);
            verify(mapper,times(0)).funkoToFunkoResponseDto(funko);

        }
        @Test
        @DisplayName("delete by id Bad")
        void deleteById() {
            when(repository.findById(1L)).thenReturn(Optional.empty());
            val result= assertThrows(FunkoNotFoundException.class, ()-> service.delete(1L));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"funko id:1 not found","deberian ser iguales")
            );
            verify(repository,times(1)).findById(1L);
            verify(repository,times(0)).deleteById(1L);
            verify(mapper,times(0)).funkoToFunkoResponseDto(funko);
        }
        @Test
        @DisplayName("patch bad")
        void patchBad() {
            when(categoriaRepository.findByNameIgnoreCase(funko.getCategoria().getName())).thenReturn(Optional.of(categoria));
            when(repository.findById(1L)).thenReturn(Optional.empty());
            val result=assertThrows(FunkoNotFoundException.class, () -> service.patch(funkoPatchRequestDto,1L));
            assertAll(
                    ()-> assertEquals(result.getMessage(),"funko id:1 not found","deberian ser iguales")
            );
            verify(repository,times(1)).findById(1L);
            verify(repository,times(0)).save(funko);
            verify(categoriaRepository, times(1)).findByNameIgnoreCase(funko.getCategoria().getName());
            verify(mapper,times(0)).funkoToFunkoResponseDto(funko);
        }
    }
}