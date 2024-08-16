package com.example.demo.Model;

import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Enums.TipoCarta;
import com.example.demo.Model.Enums.Zona;
import com.example.demo.Model.Enums.tipoEspecial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaTest {

    private Tarjeta tarjeta;
    private Jugador jugador;
    Stack<Carta> listaTest = new Stack<>();

    @BeforeEach
    public void inicializarObjetosParaUsar()
    {
        tarjeta = new Suerte();
        jugador = new Jugador("Test");
        Carta cartaTestAvz = new Carta(TipoCarta.SUERTE,"Avance",5,"Avanzar");

        Carta cartaTestRetr = new Carta(TipoCarta.SUERTE,"Retroceda 2 casilleros",2,"Retroceder");

        Carta cartaAvzCas = new Carta(TipoCarta.SUERTE,"Siga hasta Buenos Aires, Zona Norte",
                40,"AvanzarCasillero");

        Carta cartaRetrCas = new Carta(TipoCarta.DESTINO,
            "Vuelve atrás hasta Formosa Zona Sur", 1,"RetrocederCasillero");

        Carta cartaCobrar = new Carta(TipoCarta.SUERTE,"Ha ganado la grande. Cobre 10.000",
                10000,"Cobrar");

        Carta cartaPagar = new Carta(TipoCarta.SUERTE, "Multa caminera. Pague 400",
                400,"Pagar");

        Carta cartaPagarMoment = new Carta(TipoCarta.SUERTE, "Por compra de semilla pague al Banco 800 por cada chacra. 3.000 por cada estancia",
                800,"PagarEnElMomento");

        Carta cartaMPreso = new Carta(TipoCarta.SUERTE,"Marche preso directamente.",
                1,"Preso");

        Carta cartaSalirComisaria = new Carta(TipoCarta.SUERTE,"Habeas Corpus concedido. Con esta tarjeta sale usted gratuitamente de la Comisaría",
                1,"SalirComisaria");

        listaTest.add(cartaTestAvz); //0
        listaTest.add(cartaTestRetr);//1
        listaTest.add(cartaAvzCas);//2
        listaTest.add(cartaRetrCas);//3
        listaTest.add(cartaCobrar);//4
        listaTest.add(cartaPagar);//5
        listaTest.add(cartaPagarMoment);//6
        listaTest.add(cartaMPreso);//7
        listaTest.add(cartaSalirComisaria);//8

    }

    @Test
    void avanzar() {
        Carta cartaAvance = listaTest.get(0);
        int avance = cartaAvance.getCantidad();
        tarjeta.Avanzar(cartaAvance, jugador);

        assertEquals(TipoCarta.SUERTE,cartaAvance.getTipo());
        assertEquals(5,avance);
        assertEquals(5,jugador.getPosicion());
        assertEquals("Avanzar", cartaAvance.getMetodo());
        assertEquals("Avance", cartaAvance.getAccion());
    }

    @Test
    void retroceder() {
        Carta cartaRetr = listaTest.get(1);
        jugador.setPosicion(10);
        int retr = cartaRetr.getCantidad();
        tarjeta.Retroceder(cartaRetr, jugador);

        assertEquals(TipoCarta.SUERTE,cartaRetr.getTipo());
        assertEquals(2,retr);
        assertEquals(8,jugador.getPosicion());
        assertEquals("Retroceda 2 casilleros", cartaRetr.getAccion());
        assertEquals("Retroceder", cartaRetr.getMetodo());
    }

    @Test
    void avanzarCasillero() {
        Carta cartaTested = listaTest.get(2);
        int pos = cartaTested.getCantidad();
        jugador.setPosicion(16);
        int posIni = jugador.getPosicion();
        tarjeta.AvanzarCasillero(cartaTested,jugador);

        assertEquals(TipoCarta.SUERTE,cartaTested.getTipo());
        assertEquals(40,pos);
        assertEquals(40,jugador.getPosicion());
        assertNotEquals(40,posIni);
        assertEquals("Siga hasta Buenos Aires, Zona Norte", cartaTested.getAccion());
        assertEquals("AvanzarCasillero", cartaTested.getMetodo());
    }

    @Test
    void retrocederCasillero() {
      Carta cartaTested = listaTest.get(3);
        int pos = cartaTested.getCantidad();
        jugador.setPosicion(36);
        int posIni = jugador.getPosicion();
        tarjeta.RetrocederCasillero(cartaTested,jugador);

        assertEquals(TipoCarta.DESTINO,cartaTested.getTipo());
        assertEquals(1,pos);
        assertEquals(1,jugador.getPosicion());
        assertNotEquals(1,posIni);
        assertEquals("Vuelve atrás hasta Formosa Zona Sur", cartaTested.getAccion());
        assertEquals("RetrocederCasillero", cartaTested.getMetodo());
    }

    @Test
    void cobrar() {
      Carta cartaTested = listaTest.get(4);
        int cobro = cartaTested.getCantidad();
        int cuentaIni = jugador.getCuenta();
        tarjeta.Cobrar(cartaTested, jugador);

        assertEquals(TipoCarta.SUERTE,cartaTested.getTipo());
        assertEquals(10000,cobro);
        assertEquals(45000,jugador.getCuenta());
        assertNotEquals(cuentaIni, jugador.getCuenta());
        assertEquals("Ha ganado la grande. Cobre 10.000", cartaTested.getAccion());
        assertEquals("Cobrar", cartaTested.getMetodo());
    }

    @Test
    void pagar() {
      Carta cartaTested = listaTest.get(5);
        int pagar = cartaTested.getCantidad();
        int cuentaIni = jugador.getCuenta();
        tarjeta.Pagar(cartaTested, jugador);

        assertEquals(TipoCarta.SUERTE,cartaTested.getTipo());
        assertEquals(400,pagar);
        assertEquals(34600,jugador.getCuenta());
        assertNotEquals(cuentaIni, jugador.getCuenta());
        assertEquals("Multa caminera. Pague 400", cartaTested.getAccion());
        assertEquals("Pagar", cartaTested.getMetodo());
    }

    @Test
    void pagarEnElMomento() {
      Carta cartaTested = listaTest.get(6);
        int cantPagar = 0;
        int pagarPChacra = cartaTested.getCantidad();
        int cuentaIni = jugador.getCuenta();
        int acumChacras = 0;
        int acumEstancias = 0;

        Escritura forS = new Escritura(1, "Formosa Sur",
                Provincia.FORMOSA, Zona.SUR, 1000, 40, 200,
                600, 1700, 3000, 4750, 1000);
        Escritura forC = new Escritura(2, "Formosa Centro",
                Provincia.FORMOSA, Zona.CENTRO, 1000, 40, 200,
                600, 1700, 3000, 4750, 1000);
        Escritura forN = new Escritura(3, "Formosa Norte",
                Provincia.FORMOSA, Zona.NORTE, 1200, 80, 400,
                800, 3400, 6000, 9500, 1000);


        jugador.getEscrituras().add(forS);
        jugador.getEscrituras().add(forC);
        jugador.getEscrituras().add(forN);

        jugador.getEscrituras().get(0).setCantChacras(2);

        tarjeta.PagarEnElMomento(cartaTested, jugador);

        assertEquals(TipoCarta.SUERTE, cartaTested.getTipo());
        assertEquals(800,pagarPChacra);
        assertNotEquals(cuentaIni, jugador.getCuenta());
        assertNotEquals(acumChacras, jugador.getEscrituras().get(0).getCantChacras());
        assertEquals(acumEstancias, jugador.getEscrituras().get(0).getCantEstancias());
        assertNotEquals(1600,cantPagar);

    }

    @Test
    void marcharPreso() {
      Carta cartaTested = listaTest.get(7);
      int posIni = jugador.getPosicion();

      tarjeta.marcharPreso(jugador);

      assertEquals(14, jugador.getPosicion());
      assertNotEquals(posIni, jugador.getPosicion());
      assertEquals(TipoCarta.SUERTE, cartaTested.getTipo());
      assertEquals("Marche preso directamente.", cartaTested.getAccion());
      assertEquals("Preso",cartaTested.getMetodo());
    }

    @Test
    void salirComisaria() {
      Carta cartaTested = listaTest.get(8);

      Carta nullCard = jugador.getSalirComisaria();

      tarjeta.salirComisaria(jugador, cartaTested);

      assertNull(nullCard);
      assertEquals(cartaTested, jugador.getSalirComisaria());
      assertEquals(TipoCarta.SUERTE, cartaTested.getTipo());
      assertEquals("Habeas Corpus concedido. Con esta tarjeta sale usted gratuitamente de la Comisaría", cartaTested.getAccion());
      assertEquals("SalirComisaria", cartaTested.getMetodo());
    }
}

