package com.example.demo.Model.Perfil;

import com.example.demo.Model.Casillero;
import com.example.demo.Model.Escritura;
import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Jugador;

import java.util.List;

public interface Perfil {
    boolean esPreferida(Provincia provincia);
    void iniciarProvPreferidas();
    void construirMejora(Casillero casillero, Jugador jugador);
    <T extends Casillero> boolean comprarPropiedad(T casillero,Jugador jugador);
    <T extends Casillero> boolean comprarPorPreferenciaPropiedadV2(T casillero,Jugador jugador);
    <T extends Casillero> boolean comprarFueraDePreferenciaPropiedad(T casillero,Jugador jugador);
    void  contarEscriturasAdquiridasPorZona(Escritura escritura);

}
