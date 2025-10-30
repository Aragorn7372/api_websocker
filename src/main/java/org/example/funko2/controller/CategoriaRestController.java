package org.example.funko2.controller;

import jakarta.validation.Valid;
import org.example.funko2.dto.categoria.CategoriaRequestDto;
import org.example.funko2.dto.categoria.CategoriaResponseDto;
import org.example.funko2.service.categoria.CategoriaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoriaRestController {
    private final Logger logger = LoggerFactory.getLogger(CategoriaRestController.class);
    private final CategoriaServiceImpl service;
    @Autowired
    CategoriaRestController(CategoriaServiceImpl service) {
        this.service = service;
    }
    @GetMapping({"","/"})
    public ResponseEntity<List<CategoriaResponseDto>> getCategorias() {
        logger.debug("obtendo lista de categorias");
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> getCategoriaById(@PathVariable UUID id) {
        logger.debug("obteniedo categoria a traves de id");
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping("")
    public ResponseEntity<CategoriaResponseDto> saveCategoria(@Valid @RequestBody CategoriaRequestDto categoriaRequestDto) {
        logger.debug("guardando categoria");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(categoriaRequestDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> deleteCategoriaById(@PathVariable UUID id) {
        logger.debug("eliminando categoria a traves de id");
        return ResponseEntity.ok(service.delete(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> updateCategoria(@PathVariable UUID id, @Valid @RequestBody CategoriaRequestDto categoriaRequestDto) {
        logger.debug("actualizando categoria a traves de id");
        return ResponseEntity.ok(service.update(categoriaRequestDto, id));
    }

}
