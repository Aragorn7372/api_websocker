package org.example.funko2.dto.funko;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunkoPatchRequestDto {
    private String name;
    @Min(value = 0,message = "no puede ser menor a 0")
    private Double price;
    @Min(value = 0,message = "la cantidad no puede ser negativa")
    private Integer cantidad;
    private String imagen;
    @Pattern(regexp = "FANTASIA|ANIME|VIDEOJUEGOS",message = "la categoria debe de ser una de estas tres FANTASIA,ANIME,VIDEOJUEGOS ")
    private String categoria;
}
