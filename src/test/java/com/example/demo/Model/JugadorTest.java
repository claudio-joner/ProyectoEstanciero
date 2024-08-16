package com.example.demo.Model;

import com.example.demo.Model.Enums.*;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

    private Jugador jugador;
    private Dado mockDado;

    @BeforeEach
    public void inicializar() {
        jugador = new Jugador();
        mockDado = Mockito.mock(Dado.class);
    }

    @Test
    public void testPasarXSalida() {

        assertEquals(35000, jugador.getCuenta());
        jugador.pasarXSalida();
        assertEquals(40000, jugador.getCuenta());
        jugador.pasarXSalida();
        assertEquals(45000, jugador.getCuenta());
        jugador.pasarXSalida();
        assertNotEquals(1000,jugador.getCuenta());
    }

    @Test
    void iniciarDescanso() {
        jugador.iniciarDescanso();
        assertEquals(3, jugador.getTurnoRetenido());
    }

    @Test
    void descanso() {
        Mockito.when(mockDado.compararValores()).thenReturn(true);
        boolean resultado = jugador.descanso(mockDado);
        assertTrue(resultado);

        Mockito.when(mockDado.compararValores()).thenReturn(false);
        boolean resultadoFalso = jugador.descanso(mockDado);
        assertFalse(resultadoFalso);

    }

    @Test
    void finDescanso() {
        jugador.iniciarDescanso();
        jugador.finDescanso();
        assertEquals(0, jugador.getTurnoRetenido());
    }

    @Test
    public void testMatchRegex_ForInputIncorrect() {
        Jugador jugadorTest = new Jugador();
        String input_regex = "[yYnN]";
        String input = "Yes";

        assertFalse(jugadorTest.matchRegex(input,input_regex));
    }

    @Test
    public void testMatchRegex_ForInputYorN() {
        Jugador jugadorTest = new Jugador();
        String input_regex = "[yYnN]";
        String input = "Y";

        assertTrue(jugadorTest.matchRegex(input,input_regex));
    }

    @Test
    public void testMatchRegex_forDesicionIncorrect() {
        Jugador jugadorTest = new Jugador();
        String input_regex = "[eEsS]";
        String input = "Pedro";

        assertFalse(jugadorTest.matchRegex(input,input_regex));
    }

    @Test
    public void testMatchRegex_forDesicionEorS() {
        Jugador jugadorTest = new Jugador();
        String input_regex = "[eEsS]";
        String input = "s";

        assertTrue(jugadorTest.matchRegex(input,input_regex));
    }

    @Test
    void elegirPeon_CasoAZUL() {
        Jugador jugadorPeon = new Jugador();
        jugadorPeon.elegirPeon(1);

        assertEquals(Peon.AZUL,jugadorPeon.getPeon());
        assertEquals("\u001B[34m", jugadorPeon.getColor());
    }
    @Test
    void elegirPeon_CasoROJO() {
        Jugador jugadorPeon1 = new Jugador();
        jugadorPeon1.elegirPeon(2);

        assertEquals(Peon.ROJO, jugadorPeon1.getPeon());
        assertEquals("\u001B[31m", jugadorPeon1.getColor());
    }
    @Test
    void elegirPeon_CasoAMARILLO() {
        Jugador jugadorPeon2 = new Jugador();
        jugadorPeon2.elegirPeon(3);

        assertEquals(Peon.AMARILLO,jugadorPeon2.getPeon());
        assertEquals("\u001B[93m", jugadorPeon2.getColor());
    }
    @Test
    void elegirPeon_CasoVERDE() {
        Jugador jugadorPeon3 = new Jugador();
        jugadorPeon3.elegirPeon(4);

        assertEquals(Peon.VERDE,jugadorPeon3.getPeon());
        assertEquals("\u001B[32m", jugadorPeon3.getColor());
    }
    @Test
    void elegirPeon_CasoNARANJA() {
        Jugador jugadorPeon4 = new Jugador();
        jugadorPeon4.elegirPeon(5);

        assertEquals(Peon.NARANJA,jugadorPeon4.getPeon());
        assertEquals("\u001B[38;5;208m", jugadorPeon4.getColor());
    }@Test
    void elegirPeon_CasoVIOLETA() {
        Jugador jugadorPeon5 = new Jugador();
        jugadorPeon5.elegirPeon(6);

        assertEquals(Peon.VIOLETA,jugadorPeon5.getPeon());
        assertEquals("\u001B[35m", jugadorPeon5.getColor());
    }

    @Test
    void pagarCarcelDados_true() {
        Jugador jugadorCarcel = new Jugador();
        Mockito.when(mockDado.compararValores()).thenReturn(true);
        boolean resultado = jugadorCarcel.pagarCarcel(mockDado);
        assertTrue(resultado);
        assertEquals(0,jugadorCarcel.getTurnoRetenido());
    }
    @Test
    void pagarCarcelDados_false() {
        Jugador jugadorCarcel = new Jugador();
        Mockito.when(mockDado.compararValores()).thenReturn(false);
        boolean resultado = jugadorCarcel.pagarCarcel(mockDado);
        assertFalse(resultado);
        assertNotEquals(0,jugadorCarcel.getTurnoRetenido());
    }
    @Test
    void pagarCarcelDescontar() {
        Jugador jugadorCarcel1 = new Jugador();
        jugadorCarcel1.pagarCarcel();

        assertEquals(34000, jugadorCarcel1.getCuenta());
    }
    @Test
    void marchePreso() {
        Jugador jugadorCarcel2 = new Jugador();
        jugadorCarcel2.marchePreso();

        assertEquals(14, jugadorCarcel2.getPosicion());
        assertEquals(2,jugadorCarcel2.getTurnoRetenido());
    }

    @Test
    void avanzarPosicionNormal(){
        Jugador jugadorAvanza = new Jugador();
        jugadorAvanza.setPosicion(10);
        int largoTest = 42;
        int cantidadTest = 10;
        jugadorAvanza.avanzarPosicion(cantidadTest,largoTest);

        assertEquals(20,jugadorAvanza.getPosicion());
        assertEquals(35000,jugadorAvanza.getCuenta());
    }
    @Test
    void avanzarPosicionVuelta(){
        Jugador jugadorAvanza2 = new Jugador();
        jugadorAvanza2.setPosicion(40);
        int largoTest = 42;
        int cantidadTest = 5;
        jugadorAvanza2.avanzarPosicion(cantidadTest,largoTest);

        assertEquals(3,jugadorAvanza2.getPosicion());
        assertEquals(40000,jugadorAvanza2.getCuenta());
    }

    @Test
    void calcularPatrimonio(){
        Jugador jugadorPatrimonio = new Jugador();
        Integer cuentaJug = jugadorPatrimonio.getCuenta();

        assertEquals(cuentaJug,jugadorPatrimonio.calcularPatrimonio());
    }

    @Test
    void calcularPatrimonioWithEscrituras(){
        Jugador jugadorPatrimonio = new Jugador();
        Escritura forS = new Escritura(1, "Formosa Sur",
                Provincia.FORMOSA, Zona.SUR, 1000, 40, 200,
                600, 1700, 3000, 4750, 1000);
        jugadorPatrimonio.getEscrituras().add(forS);

        assertEquals(36000,jugadorPatrimonio.calcularPatrimonio());
    }

    @Test
    void calcularPatrimonioWithServicios(){
        Jugador jugadorPatrimonio = new Jugador();
        Servicio p = new Servicio(8, "Compañía Petrolera", TipoServicio.PETROLERA);
        jugadorPatrimonio.getServicios().add(p);

        assertEquals(38800,jugadorPatrimonio.calcularPatrimonio());
    }


    @Test
    void pagarCarcel() {
        Jugador jugador = new Jugador();
        Carta carta = new Carta(TipoCarta.SUERTE, "Habeas Corpus concedido. Con esta tarjeta sale usted gratuitamente de la Comisaría", 1, "SalirComisaria");
        jugador.setSalirComisaria(carta);

        Stack<Carta> cartas = new Stack<>();

        jugador.pagarCarcel(cartas);

        assertEquals(0, jugador.getTurnoRetenido());
        assertNull(jugador.getSalirComisaria());
        assertTrue(cartas.contains(carta));

    }

}

