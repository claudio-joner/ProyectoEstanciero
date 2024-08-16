package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Casillero {
    private Integer posicion;
    private String descripcion;

    public Casillero(Integer posicion) {
        this.posicion = posicion;
    }
}

