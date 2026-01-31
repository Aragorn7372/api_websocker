package org.example.funko2.controller;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.service.categoria.CategoriaServiceImpl;
import  org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.funko2.model.Categoria;

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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CategoriaRestControllerTest {
    @MockitoBean
    CategoriaServiceImpl service;
    @Autowired
    MockMvc mockMvc;

    String myEndpoint = "/categorias";
    private final Categoria categoria= Categoria
            .builder()
            .id(UUID.fromString("4b23bd64-c198-4eda-9d84-d4bdb0e5a24f"))
            .name("ANIME")
            .fechaCreacion(LocalDateTime.now())
            .fechaModificacion(LocalDateTime.now())
            .build();
    private final CategoriaResponseDto categoriaResponseDto= CategoriaResponseDto.builder().id(categoria.getId()).name(categoria.getName()).build();
    private final CategoriaRequestDto categoriaRequestDto= CategoriaRequestDto.builder().name(categoria.getName()).build();
    @Test
    void getCategorias() throws Exception {
        when(service.findAll()).thenReturn(List.of(categoriaResponseDto));
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<CategoriaResponseDto> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, CategoriaResponseDto.class));

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertFalse(res.isEmpty(), "La lista no debe estar vacía"),
                () -> assertEquals(categoria.getId(), res.get(0).getId()),
                () -> assertEquals(categoria.getName(), res.get(0).getName())
        );

        verify(service,times(1)).findAll();
    }

    @Test
    void getFunkoById() throws Exception {
        when(service.findById(categoria.getId())).thenReturn(categoriaResponseDto);
        MockHttpServletResponse response= mockMvc.perform(
                get(myEndpoint+"/"+categoria.getId()).accept(String.valueOf(MediaType.APPLICATION_JSON))
        ).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        CategoriaResponseDto res= mapper.readValue(response.getContentAsString(),CategoriaResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), categoria.getId())
        );
        verify(service,times(1)).findById(categoria.getId());
    }

    @Test
    void createTest() throws Exception {

        when(service.save(categoriaRequestDto))
                .thenReturn(categoriaResponseDto);
        ObjectMapper mapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndpoint)
                                .contentType(MediaType.APPLICATION_JSON) // Indicamos el tipo de contenido
                                .content(mapper.writeValueAsString(categoriaRequestDto))) // Indicamos el contenido como JSON
                .andReturn().getResponse();


        CategoriaResponseDto res = mapper.readValue(response.getContentAsString(), CategoriaResponseDto.class);

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
                () -> assertEquals(res.getId(), categoria.getId())
        );

        // Verificamos que se ha llamado al método
        verify(service, times(1))
                .save(categoriaRequestDto);

    }


    @Test
    void updateFunko() throws Exception {
        when(service.update(categoriaRequestDto,categoria.getId())).thenReturn(categoriaResponseDto);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                put(myEndpoint+"/"+categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoriaRequestDto))
        ).andReturn().getResponse();
        CategoriaResponseDto res = mapper.readValue(response.getContentAsString(), CategoriaResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), categoria.getId())
        );
        verify(service, times(1)).update(categoriaRequestDto,categoria.getId());
    }
    @Test
    void deleteFunko() throws Exception {
        when(service.delete(categoria.getId())).thenReturn(categoriaResponseDto);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                delete(myEndpoint+"/"+categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        CategoriaResponseDto res = mapper.readValue(response.getContentAsString(), CategoriaResponseDto.class);
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(res.getId(), categoria.getId())
        );
        verify(service, times(1)).delete(categoria.getId());
    }
    @Test
    void findByIdNotFound() throws Exception {
        when(service.findById(categoria.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la funko con uuid: "+categoria.getId()));
        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint + "/" + categoria.getId())
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        // Proceso la respuesta
        try {
            CategoriaResponseDto res = mapper.readValue(response.getContentAsString(), CategoriaResponseDto.class);
        } catch (Exception ignored) {
        }

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );

        // Verificamos que se ha llamado al método
        verify(service, times(1))
                .findById(categoria.getId());
    }
    @Test
    void updateFunkoNotFound() throws Exception {
        when(service.update(categoriaRequestDto,categoria.getId()   )).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND,"no se ha encontrado funko con uuid: "+categoria.getId())
        );
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response= mockMvc.perform(
                put(myEndpoint+"/"+categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoriaRequestDto))
        ).andReturn().getResponse();
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );
        verify(service, times(1)).update(categoriaRequestDto,categoria.getId());
    }
    @Test
    void deleteNotFound() throws Exception {
        when(service.delete(categoria.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la funko con uuid: "+categoria.getId()));
        MockHttpServletResponse response = mockMvc.perform(
                        delete(myEndpoint + "/" + categoria.getId())
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();
        // Proceso la respuesta
        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value())
        );

        // Verificamos que se ha llamado al método
        verify(service, times(1))
                .delete(categoria.getId());
    }

}