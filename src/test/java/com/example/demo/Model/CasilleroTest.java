package com.example.demo.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CasilleroTest {
    @Test
    public void testConstructorCompleto() {
        Casillero casillero = new CasilleroEspecial(1, "Inicio");

        assertEquals(1, casillero.getPosicion());
        assertEquals("Inicio", casillero.getDescripcion());
    }

    @Test
    public void testConstructorPosicion() {
        Casillero casillero = new CasilleroEspecial(2);

        assertEquals(2, casillero.getPosicion());
        assertNull(casillero.getDescripcion());
    }

    @Test
    public void testSetPosyDesc() {
        Casillero casillero = new CasilleroEspecial(2, "Salida");

        casillero.setPosicion(10);
        casillero.setDescripcion("Destino");

        assertNotEquals(2, casillero.getPosicion());
        assertNotNull(casillero.getDescripcion());
        assertNotEquals("Salida",casillero.getDescripcion());
        assertTrue(casillero.getPosicion().equals(10));
    }


}