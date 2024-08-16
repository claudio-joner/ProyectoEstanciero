package com.example.demo.Model;

import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Enums.Zona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EscrituraTest {

    private Escritura escritura;

    @BeforeEach
    void setUp() {
        escritura = new Escritura();
    }

    @Test
    void testValorTotalNoHipotecada() {
        escritura.setEstadoHipoteca(false);
        escritura.setValorEscritura(1000);
        escritura.setCantChacras(2);
        escritura.setCostoConstruccionChacra(200);
        escritura.setCantEstancias(1);
        escritura.setCostoConstruccionEstancia(300);
        int valorTotal = escritura.ValorTotal();
        assertEquals(1700, valorTotal);
    }

    @Test
    void testValorTotalHipotecada() {

        escritura.setEstadoHipoteca(true);

        int valorTotal = escritura.ValorTotal();

        assertEquals(0, valorTotal);
    }

    @Test
    void ValorTotalTest(){
        Escritura e = new Escritura();
        e.setValorEscritura(1000);
        e.setCostoConstruccionEstancia(1000);
        e.setCantEstancias(0);
        e.setCostoConstruccionChacra(1000);
        e.setCantChacras(0);
        e.setEstadoHipoteca(false);

        //sin chacras o estancias
        int total= e.getValorEscritura();
        assertEquals(e.ValorTotal(), total);

        //con chacras pero sin estancias
        e.setCantChacras(2);
        int total2 = total + e.getCantChacras()*e.getCostoConstruccionChacra();
        assertEquals(e.ValorTotal(), total2);

        //con ambas
        e.setCantEstancias(1);
        int total3 = total2 + e.getCantEstancias()*e.getCostoConstruccionEstancia();
        assertEquals(e.ValorTotal(), total3);
    }

    @Test
    void ProvinciaCompletaTest(){
        Jugador duenio = new Jugador();

        //formosa
        Escritura f1 = new Escritura();
        f1.setProvincia(Provincia.FORMOSA);
        Escritura f2 = new Escritura();
        f2.setProvincia(Provincia.FORMOSA);
        Escritura f3 = new Escritura();
        f3.setProvincia(Provincia.FORMOSA);
        duenio.getEscrituras().add(f1); duenio.getEscrituras().add(f2);
        f1.setDuenio(duenio); f2.setDuenio(duenio);

        //tucuman
        Escritura t1 = new Escritura();
        t1.setProvincia(Provincia.TUCUMAN);
        Escritura t2 = new Escritura();
        t2.setProvincia(Provincia.TUCUMAN);
        duenio.getEscrituras().add(t1);
        t1.setDuenio(duenio);

        //duenio tiene la Provincia Incompleta (de 2 y 3 Zonas)
        assertFalse(f1.ProvinciaCompleta(f1));
        assertFalse(f1.ProvinciaCompleta(f2));
        assertFalse(f1.ProvinciaCompleta(f3));
        assertFalse(f1.ProvinciaCompleta(t1));
        assertFalse(f1.ProvinciaCompleta(t2));

        //Provincia de 2 Zonas Completa (Tucuman)
        //duenio completa la provincia tucuman
        duenio.getEscrituras().add(t2); t2.setDuenio(duenio);
        assertTrue(t1.ProvinciaCompleta(t1));
        assertTrue(t1.ProvinciaCompleta(t2));

        //Provincia de 3 Zonas Completa (Formosa)
        //duenio completa la provincia formosa
        duenio.getEscrituras().add(f3); f3.setDuenio(duenio);
        assertTrue(f1.ProvinciaCompleta(f1));
        assertTrue(f1.ProvinciaCompleta(f2));
        assertTrue(f1.ProvinciaCompleta(f3));
    }

    @Test
    void CobrarAlquilerTest(){
        Jugador duenio = new Jugador();

        Jugador j = new Jugador();
        j.setCuenta(100000);

        Escritura e1 = new Escritura();
        e1.setProvincia(Provincia.CORDOBA);
        Escritura e2 = new Escritura();
        e2.setProvincia(Provincia.CORDOBA);

        Escritura escritura = new Escritura(32, "Córdoba Sur",
                Provincia.CORDOBA, Zona.SUR, 6000, 500, 1250,
                6500, 17000, 21000, 24000, 3000);

        //para cobrar alquiler la provincia tiene q tener un dueño, si no, no se cobra
        escritura.setDuenio(duenio);

        //alquiler con solo el campo
        assertEquals(escritura.getAlqCampoSolo(), escritura.CobrarAlquiler(j));

        //alquiler con 1 chacra
        escritura.setCantChacras(1);
        assertEquals(escritura.getAlq1Chacra(), escritura.CobrarAlquiler(j));

        //alquiler con 2 chacras
        escritura.setCantChacras(2);
        assertEquals(escritura.getAlq2Chacra(), escritura.CobrarAlquiler(j));

        //alquiler con 3 chacras
        escritura.setCantChacras(3);
        assertEquals(escritura.getAlq3Chacra(), escritura.CobrarAlquiler(j));

        //alquiler con 4 chacras
        escritura.setCantChacras(4);
        assertEquals(escritura.getAlq4Chacra(), escritura.CobrarAlquiler(j));

        //alquiler con 1 estancia
        escritura.setCantEstancias(1);
        assertEquals(escritura.getAlq1Estancia(), escritura.CobrarAlquiler(j));

        //si el dueño de la escritura tiene esa Provincia completa, el alquiler se duplica
        duenio.getEscrituras().add(e1); duenio.getEscrituras().add(e2); duenio.getEscrituras().add(escritura);
        assertEquals(escritura.getAlq1Estancia()*2, escritura.CobrarAlquiler(j));
    }
}