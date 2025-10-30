package org.example.funko2.controller;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import  org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.example.funko2.service.funko.FunkoServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class FunkoRestControllerTest {
    @MockitoBean
    FunkoServiceIMPL service;
    @Autowired
    MockMvc mockMvc;

    String myEndpoint = "/funkos";
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

    @Test
    void getFunkoByName() throws Exception {
        when(service.findByName(funko.getName())).thenReturn(List.of(funkoResponse));
        MockHttpServletResponse response= mockMvc.perform(
                get(myEndpoint+"/name/"+funko.getName()).accept(String.valueOf(MediaType.APPLICATION_JSON))
        ).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        List<FunkoResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, FunkoResponseDto.class));
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + funkoResponse.getId())),
                () -> assertTrue(res.size() > 0)
        );
        verify(service,times(1)).findByName(funko.getName());
    }
    @Test
    void getFunkoByPrice() throws Exception {
        when(service.findByPrecioMenor(funko.getPrice())).thenReturn(List.of(funkoResponse));
        MockHttpServletResponse response= mockMvc.perform(
                get(myEndpoint+"/min/price/"+funko.getPrice()).accept(String.valueOf(MediaType.APPLICATION_JSON))
        ).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        List<FunkoResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, FunkoResponseDto.class));
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + funkoResponse.getId())),
                () -> assertTrue(res.size() > 0)
        );
        verify(service,times(1)).findByPrecioMenor(funko.getPrice());
    }

    @Test
    void getFunkoByCategory() throws Exception {
        when(service.findByCategory(funko.getCategoria().getName())).thenReturn(List.of(funkoResponse));
        MockHttpServletResponse response= mockMvc.perform(
                get(myEndpoint+"/category/"+funko.getCategoria().getName()).accept(String.valueOf(MediaType.APPLICATION_JSON))
        ).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        List<FunkoResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, FunkoResponseDto.class));
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value(),"deberia devolver un ok"),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + funkoResponse.getId()),"deberia tener la misma id"),
                () -> assertTrue(res.size() > 0,"deberia tener mas de size 1")
        );
        verify(service,times(1)).findByCategory(funko.getCategoria().getName());
    }
    @GetMapping("/uuid/{uuid}")
    @Test
    void getFunkoByUuid() throws Exception {
        when(service.findByUuid(funko.getUuid())).thenReturn(funkoResponse);
        MockHttpServletResponse response= mockMvc.perform(
                get(myEndpoint+"/uuid/"+funko.getUuid()).accept(String.valueOf(MediaType.APPLICATION_JSON))
        ).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        FunkoResponseDto res= mapper.readValue(response.getContentAsString(),FunkoResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + funkoResponse.getId()))
        );
        verify(service,times(1)).findByUuid(funko.getUuid());
    }
    @Test
    void getAllFunkos() throws Exception {
        when(service.findAll()).thenReturn(List.of(funkoResponse));
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint)
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        List<FunkoResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, FunkoResponseDto.class));
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertTrue(response.getContentAsString().contains("\"id\":" + funkoResponse.getId())),
                () -> assertTrue(res.size() > 0)
        );
        verify(service,times(1)).findAll();

    }

    @Test
    void getFunkoById() throws Exception {
        when(service.findById(funko.getId())).thenReturn(funkoResponse);
        MockHttpServletResponse response= mockMvc.perform(
                get(myEndpoint+"/"+funko.getId()).accept(String.valueOf(MediaType.APPLICATION_JSON))
        ).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        FunkoResponseDto res= mapper.readValue(response.getContentAsString(),FunkoResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), funkoResponse.getId())
        );
        verify(service,times(1)).findById(funko.getId());
    }

    @Test
    void createTest() throws Exception {

        when(service.save(funkoRequestDto))
                .thenReturn(funkoResponse);
        ObjectMapper mapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(
                post(myEndpoint)
                        .contentType(MediaType.APPLICATION_JSON) // Indicamos el tipo de contenido
                        .content(mapper.writeValueAsString(funkoRequestDto))) // Indicamos el contenido como JSON
                        .andReturn().getResponse();


        FunkoResponseDto res = mapper.readValue(response.getContentAsString(), FunkoResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
                () -> assertEquals(res.getId(), funko.getId())
        );

        // Verificamos que se ha llamado al método
        verify(service, times(1))
                .save(funkoRequestDto);

    }


    @Test
    void updateFunko() throws Exception {
        when(service.update(funkoRequestDto,funko.getId())).thenReturn(funkoResponse);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                put(myEndpoint+"/"+funko.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(funkoRequestDto))
        ).andReturn().getResponse();
        FunkoResponseDto res = mapper.readValue(response.getContentAsString(), FunkoResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), funko.getId())
        );
        verify(service, times(1)).update(funkoRequestDto,funko.getId());
    }

    @Test
    void patchFunko() throws Exception {
        when(service.patch(funkoPatchRequestDto,funko.getId())).thenReturn(funkoResponse);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                patch(myEndpoint+"/"+funko.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(funkoPatchRequestDto))
        ).andReturn().getResponse();
        FunkoResponseDto res = mapper.readValue(response.getContentAsString(), FunkoResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), funko.getId())
        );
        verify(service, times(1)).patch(funkoPatchRequestDto,funko.getId());
    }

    @Test
    void deleteFunko() throws Exception {
        when(service.delete(funko.getId())).thenReturn(funkoResponse);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                delete(myEndpoint+"/"+funko.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        FunkoResponseDto res = mapper.readValue(response.getContentAsString(), FunkoResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), funko.getId())
        );
        verify(service, times(1)).delete(funko.getId());
    }
    @Test
    void findByIdNotFound() throws Exception {
        when(service.findById(-1000L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la funko con id: -1000"));
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/" + -1000L)
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        // Proceso la respuesta
        try {
            FunkoResponseDto res = mapper.readValue(response.getContentAsString(), FunkoResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );

        // Verificamos que se ha llamado al método
        verify(service, times(1))
                .findById(-1000L);
    }
    @Test
    void updateFunkoNotFound() throws Exception {
        when(service.update(funkoRequestDto,-1000L)).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND,"no se ha encontrado funko con id: -1000")
        );
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                put(myEndpoint+"/"+-1000L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(funkoRequestDto))
        ).andReturn().getResponse();
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
        verify(service, times(1)).update(funkoRequestDto,-1000L);
    }
    @Test
    void patchFunkoNotFound() throws Exception {
        when(service.patch(funkoPatchRequestDto,-1000L)).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND,"no se ha encontrado funko con id: -1000")
        );
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                patch(myEndpoint+"/"+-1000L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(funkoRequestDto))
        ).andReturn().getResponse();
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
        verify(service, times(1)).patch(funkoPatchRequestDto,-1000L);
    }
    @Test
    void deleteNotFound() throws Exception {
        when(service.delete(-1000L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la funko con id: -1000"));
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + -1000L)
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        // Proceso la respuesta
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );

        // Verificamos que se ha llamado al método
        verify(service, times(1))
                .delete(-1000L);
    }
}