package com.example.demo.Model;

import com.example.demo.Model.Carta;
import com.example.demo.Model.Destino;
import com.example.demo.Model.Enums.TipoCarta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DestinoTest {

    @Test
    void MazoIsEmpty() {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.getListaDestino();

        Assertions.assertTrue(listaDestino.isEmpty());
    }
    @Test
    void MazoIsNotEmpty() {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();

        Assertions.assertFalse(listaDestino.isEmpty());
    }
    @Test
    void MazoHaveCards() {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();
        Stack<Carta> listaEmpty = new Stack<>();

        Assertions.assertNotEquals(listaDestino,listaEmpty);
    }

    @Test
    void MazoHave18Cards()
    {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();

        assertEquals(listaDestino.size(), 18);
    }

    @Test
    void MazoCardsCreatedInPosition()
    {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();

        String resultado = listaDestino.get(2).getMetodo();
        assertEquals("Preso", resultado);
    }

    @Test
    void MazoCardsCreatedNotInPosition()
    {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();

        String resultado = listaDestino.get(10).getMetodo();
        assertNotEquals("Preso", resultado);
    }

    @Test
    void PopMazo()
    {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();

        listaDestino.pop();
        assertEquals(17, listaDestino.size());
    }

    @Test
    void setMazo()
    {
        Destino destino = new Destino();
        Stack<Carta> listaDestino = destino.GenerarMazo();

        destino.setListaDestino(listaDestino);

        assertFalse(destino.getListaDestino().isEmpty());
    }

}