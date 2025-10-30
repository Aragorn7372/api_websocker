package org.example.funko2.controller;




import jakarta.validation.Valid;
import org.example.funko2.dto.funko.FunkoPatchRequestDto;
import org.example.funko2.dto.funko.FunkoRequestDto;
import org.example.funko2.dto.funko.FunkoResponseDto;
import org.example.funko2.service.funko.FunkoServiceIMPL;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funkos")
public class FunkoRestController {
    private final Logger logger = LoggerFactory.getLogger(FunkoRestController.class);
    private final FunkoServiceIMPL service;
    @Autowired
    public FunkoRestController(FunkoServiceIMPL service)
    {
        logger.debug("FunkoServiceIMPL creandose");
        this.service = service;
    }
    @GetMapping({"","/"})
    public ResponseEntity<List<FunkoResponseDto>> getAllFunkos(){
        logger.debug("FunkoServiceIMPL getAllFunkos");
        return  ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FunkoResponseDto> getFunkoById(@PathVariable Long id){
        logger.debug("FunkoServiceIMPL getFunkoById");
        return  ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<FunkoResponseDto>> getFunkoByName(@PathVariable String name){
        logger.debug("FunkoServiceIMPL getFunkoByName");
        return  ResponseEntity.ok(service.findByName(name));
    }
    @GetMapping("/min/price/{price}")
    public ResponseEntity<List<FunkoResponseDto>> getFunkoByPrice(@PathVariable Double price){
        logger.debug("FunkoServiceIMPL getFunkoByPrice");
        return ResponseEntity.ok(service.findByPrecioMenor(price));
    }
    @GetMapping("/category/{categoria}")
    public ResponseEntity<List<FunkoResponseDto>> getFunkoByCategory(@PathVariable String categoria){
        logger.debug("FunkoServiceIMPL getFunkoByCategory");
        return ResponseEntity.ok(service.findByCategory(categoria));
    }
    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<FunkoResponseDto> getFunkoByUuid(@PathVariable UUID uuid){
        logger.debug("FunkoServiceIMPL getFunkoByUuid");
        return ResponseEntity.ok(service.findByUuid(uuid));
    }
    @PostMapping("")
    public ResponseEntity<FunkoResponseDto> createFunko(
            @Valid @RequestBody FunkoRequestDto funko){
        logger.debug("FunkoServiceIMPL createFunko");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(funko));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FunkoResponseDto> updateFunko(@PathVariable Long id,
                                                        @Valid @RequestBody FunkoRequestDto funko){
        logger.debug("FunkoServiceIMPL updateFunko");

        return ResponseEntity.ok(service.update(funko, id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<FunkoResponseDto> patchFunko(@PathVariable Long id,
                                                       @Valid @RequestBody FunkoPatchRequestDto funko){
        logger.debug("FunkoServiceIMPL patchFunko");

        return ResponseEntity.ok(service.patch(funko,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<FunkoResponseDto> deleteFunko(@PathVariable Long id){
        logger.debug("FunkoServiceIMPL deleteFunko");
        return ResponseEntity.ok(service.delete(id));
    }
}
