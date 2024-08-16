package com.example.demo.Model;

import com.example.demo.Model.Enums.EstadoPartida;
import com.example.demo.Model.Enums.TipoPartida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
class PartidaTest {

    private Partida partida;

    @BeforeEach
    public void setUp() {
        partida = new Partida();

    }
    @Test
    public void iniPartida(){
        Partida partida = new Partida();
        partida.setTipoPartida(TipoPartida.MONTOMAX);

        assertEquals(0,partida.getMontoVictoria());
        assertEquals(0,partida.getJugador().size());
        assertNotNull(partida.getJugador());
        assertEquals(TipoPartida.MONTOMAX, partida.getTipoPartida());
        assertEquals(EstadoPartida.ENJUEGO, partida.getEstadoPartida());
        assertNull(partida.getFechaFinalizacion());
        assertEquals("\u001B[0m", partida.getANSI_RESET());
        assertNotNull(partida.getScanner());
        assertNotNull(partida.getTable());
        assertNotNull(partida.getBanco());
    }

    @Test
    void reiniciarDatos() {
        partida.getJugador().add(new Jugador());

        partida.reiniciarDatos();

        assertEquals(0, partida.getId());
        assertEquals(0, partida.getMontoVictoria());
        assertEquals(EstadoPartida.ENJUEGO, partida.getEstadoPartida());
        assertNull(partida.getTipoPartida());
        assertNotNull(partida.getFechaCreacion());
        assertTrue(partida.getJugador().isEmpty());
    }
}