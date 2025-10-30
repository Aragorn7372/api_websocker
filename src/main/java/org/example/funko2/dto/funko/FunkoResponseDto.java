package org.example.funko2.dto.funko;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FunkoResponseDto {

    private long id;
    private UUID uuid;
    private String name;
    private Double price;
    private Integer cantidad;
    private String imagen;
    private String categoria;
}
