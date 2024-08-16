package com.example.demo.Model;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DadoTest {
    Dado dado;
    @BeforeEach
    public void iniDados()
    {
        dado = new Dado();
    }
    @Test
    void tirarDadosEquals() {
        Integer tirar = dado.tirarDados();
        Integer tirada = dado.getValorDadoA() + dado.getValorDadoB();

        assertEquals(tirada,tirar);
    }
    @Test
    void compararValoresTrue() {
        dado.setValorDadoA(5);
        dado.setValorDadoB(5);
        Boolean result = dado.compararValores();

        assertTrue(result);
    }
    @Test
    void compararValoresFalse() {
        dado.setValorDadoA(3);
        dado.setValorDadoB(5);
        Boolean result = dado.compararValores();

        assertFalse(result);
    }
}