package com.example.demo.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class SuerteTest {

    @Test
    void MazoIsEmpty() {
        Suerte suerte = new Suerte();
        Stack<Carta> listaSuerte = suerte.getListaSuerte();

        Assertions.assertTrue(listaSuerte.isEmpty());
    }
    @Test
    void MazoIsNotEmpty() {
        Suerte suerte = new Suerte();
        Stack<Carta> listaSuerte = suerte.GenerarMazo();

        Assertions.assertFalse(listaSuerte.isEmpty());
    }
    @Test
    void MazoHaveCards() {
        Suerte suerte = new Suerte();
        Stack<Carta> listaSuerte = suerte.GenerarMazo();
        Stack<Carta> listaEmpty = new Stack<>();

        Assertions.assertNotEquals(listaSuerte,listaEmpty);
    }

    @Test
    void MazoHave18Cards()
    {
        Suerte suerte = new Suerte();
        Stack<Carta> listaSuerte = suerte.GenerarMazo();

        assertEquals(listaSuerte.size(), 18);
    }

    @Test
    void setMazo()
    {
        Suerte suerte = new Suerte();
        Stack<Carta> listaSuerte = suerte.GenerarMazo();

        suerte.setListaSuerte(listaSuerte);

        assertFalse(suerte.getListaSuerte().isEmpty());
    }
}