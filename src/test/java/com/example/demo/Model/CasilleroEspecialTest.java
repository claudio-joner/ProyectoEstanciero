package com.example.demo.Model;

import com.example.demo.Model.Carta;
import com.example.demo.Model.CasilleroEspecial;
import com.example.demo.Model.Enums.tipoEspecial;
import com.example.demo.Model.Jugador;
import com.example.demo.Model.Tablero;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CasilleroEspecialTest {

    private CasilleroEspecial casillero;
    private Jugador jugadorMock;
    private Tablero tableroMock = Tablero.getTableroInstance();

    @Before
    public void inicializar() {
        casillero = new CasilleroEspecial(1, "Descripción", tipoEspecial.SALIDA);
        jugadorMock = mock(Jugador.class);
        tableroMock = mock(Tablero.class);
        ArrayList<Casillero> listaCasilleros = tableroMock.getCasilleros();
        Stack<Carta> listaSuerte = tableroMock.getListaSuerte();
        Stack<Carta> listaDestino = tableroMock.getListaDestino();


    }

    @Test
    public void testAccionMPRESO() {
        casillero = new CasilleroEspecial(1, "Descripción", tipoEspecial.MPRESO);


        casillero.accion(jugadorMock, tableroMock,casillero.getTipo());

        verify(jugadorMock, times(1)).marchePreso();
    }

    @Test
    public void testAccionPREMIO() {
        casillero = new CasilleroEspecial(7, "Descripción", tipoEspecial.PREMIO);


        casillero.accion(jugadorMock, tableroMock,casillero.getTipo());

        verify(jugadorMock, times(1)).setCuenta(anyInt());
    }

    @Test
    public void testAccionIMPUESTO() {
        casillero = new CasilleroEspecial(4, "Descripción", tipoEspecial.IMPUESTO);

        casillero.accion(jugadorMock, tableroMock, casillero.getTipo());

        verify(jugadorMock, times(1)).setCuenta(anyInt());
    }

    @Test
    public void testAccionSALIDA() {
        casillero = new CasilleroEspecial(1, "Descripción", tipoEspecial.SALIDA);

        casillero.accion(jugadorMock, tableroMock, casillero.getTipo());

        verify(jugadorMock, times(1)).pasarXSalida();
    }

    @Test
    public void testAccionSUERTE() {
        CasilleroEspecial casilleroSpy = spy(new CasilleroEspecial(36, "Suerte", tipoEspecial.SUERTE));

        Stack<Carta> listaSuerteMock = new Stack<>();
        Carta cartaMock = mock(Carta.class);
        when(cartaMock.getMetodo()).thenReturn("Avanzar");
        listaSuerteMock.push(cartaMock);

        when(tableroMock.getListaSuerte()).thenReturn(listaSuerteMock);
        casilleroSpy.accion(jugadorMock, tableroMock, tipoEspecial.SUERTE);

        verify(casilleroSpy, times(1)).tomarCarta(jugadorMock, listaSuerteMock);
    }

    @Test
    public void testAccionDESTINO() {
        CasilleroEspecial casilleroSpy = spy(new CasilleroEspecial(10, "Destino", tipoEspecial.DESTINO));
        Stack<Carta> listaDestinoMock = new Stack<>();
        Carta cartaMock = mock(Carta.class);
        when(cartaMock.getMetodo()).thenReturn("Avanzar");
        listaDestinoMock.push(cartaMock);

        when(tableroMock.getListaDestino()).thenReturn(listaDestinoMock);
        casilleroSpy.accion(jugadorMock, tableroMock, casilleroSpy.getTipo());

        verify(casilleroSpy, times(1)).tomarCarta(jugadorMock, listaDestinoMock);
    }


    @Test
    public void testAccionDESCANSO() {
        casillero = new CasilleroEspecial(1, "Descripción", tipoEspecial.DESCANSO);

        casillero.accion(jugadorMock, tableroMock,casillero.getTipo());

        verify(jugadorMock, times(1)).iniciarDescanso();
    }

    @Test
    public void testAccionCARCEL_ELIBRE() {
        casillero = new CasilleroEspecial(1, "Descripción", tipoEspecial.CARCEL);

        casillero.accion(jugadorMock, tableroMock, casillero.getTipo());

        verify(jugadorMock, never()).marchePreso();
        verify(jugadorMock, never()).setCuenta(anyInt());
        verify(jugadorMock, never()).pasarXSalida();
        verify(jugadorMock, never()).iniciarDescanso();
    }
}