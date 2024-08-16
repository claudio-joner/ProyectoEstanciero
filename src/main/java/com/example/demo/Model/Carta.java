package com.example.demo.Model;


import com.example.demo.Model.Enums.TipoCarta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Carta {
    private TipoCarta tipo;
    private String accion;
    private Integer cantidad;
    private String metodo;

    public Carta(TipoCarta tipo, String accion, Integer cantidad, String metodo) {
        this.tipo = tipo;
        this.accion = accion;
        this.cantidad = cantidad;
        this.metodo = metodo;
    }

}
