package com.example.demo.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BancoTest {

    private Banco banco;

    @BeforeEach
    public void inicializarBanco() {

        banco = Banco.getBancoInstance();

    }

    @Test
    public void testVenderChacras() {
        banco.setCantidadChacras(32);
        assertEquals(31, banco.venderChacras());
    }

    @Test
    public void testVenderEstancias() {
        banco.setCantidadEstancias(12);
        assertEquals(11, banco.venderEstancias());
    }

    @Test
    public void testReiniciarBanco() {
        banco.venderChacras();
        banco.venderEstancias();

        banco.reiniciarBanco();
        banco = Banco.getBancoInstance();


        assertEquals(32, banco.getCantidadChacras());
        assertEquals(12, banco.getCantidadEstancias());
        assertEquals(29, banco.getCantidadPropiedades());
    }

    @Test
    public void testBancoInstance() {
        Banco banco1 = Banco.getBancoInstance();
        Banco banco2 = Banco.getBancoInstance();
        assertEquals(banco1, banco2);
    }
}



