package com.example.demo.dtos;

import com.example.demo.Model.Enums.TipoPartida;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartidaDto {
    private Long id;
    private LocalDateTime fechaCreacion;
    private TipoPartida tipoPartida;

}
