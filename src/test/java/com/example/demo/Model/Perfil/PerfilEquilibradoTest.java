package com.example.demo.Model.Perfil;
import com.example.demo.Model.Banco;
import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Enums.TipoServicio;
import com.example.demo.Model.Enums.Zona;
import com.example.demo.Model.Escritura;
import com.example.demo.Model.Jugador;
import com.example.demo.Model.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PerfilEquilibradoTest {
    private PerfilEquilibrado perfilEquilibrado;
    private Escritura escritura;
    private Jugador jugador;
    private Banco banco;
    @BeforeEach
    void setUp() {
        perfilEquilibrado = new PerfilEquilibrado();
        escritura = mock(Escritura.class);
        jugador = mock(Jugador.class);
        banco = mock(Banco.class);
        perfilEquilibrado.setBanco(banco);
        perfilEquilibrado.setCantidadPropiedadesTotales(0);
        perfilEquilibrado.setCantidadConstrucciones(0);
        escritura.setDuenio(jugador);


    }
    @Test
    void esPreferida() {
        assertTrue(perfilEquilibrado.esPreferida(Provincia.MENDOZA));
        assertTrue(perfilEquilibrado.esPreferida(Provincia.SANTA_FE));
        assertTrue(perfilEquilibrado.esPreferida(Provincia.TUCUMAN));
        assertFalse(perfilEquilibrado.esPreferida(Provincia.FORMOSA));
    }

    @Test
    void iniciarProvPreferidas() {
        assertEquals(Arrays.asList(Provincia.MENDOZA, Provincia.SANTA_FE, Provincia.TUCUMAN), perfilEquilibrado.getProvinciasPreferidas());
        assertNotEquals(Arrays.asList(Provincia.FORMOSA, Provincia.RIO_NEGRO, Provincia.SALTA), perfilEquilibrado.getProvinciasPreferidas());
    }

    @Test
    void construirMejora() {

        PerfilEquilibrado perEq = new PerfilEquilibrado();

        Jugador jota = new Jugador();
        jota.setCuenta(10000);
        jota.setNombre("Bot Equilibrado");
        jota.setBot(true);
        jota.setPosicion(20);

        Escritura tuc1 = new Escritura();
        tuc1.setDuenio(jota);
        tuc1.setCantChacras(0);
        tuc1.setCantEstancias(0);
        tuc1.setCostoConstruccionChacra(3000);
        tuc1.setCostoConstruccionEstancia(tuc1.getCostoConstruccionChacra()); // costoEstancia = costoChacra * 5
        tuc1.setProvincia(Provincia.TUCUMAN);
        tuc1.setZona(Zona.NORTE);

        Escritura tuc2 = new Escritura();
        tuc2.setProvincia(Provincia.TUCUMAN);
        tuc2.setDuenio(jota);
        tuc2.setZona(Zona.SUR);

        // si no tiene todas las escrituras de una provincia, no puede construir
        perEq.getBanco().setCantidadChacras(32);
        perEq.construirMejora(tuc1,jota);

        assertEquals(10000,tuc1.getDuenio().getCuenta());
        assertEquals(0, tuc1.getCantChacras());
        //banco sigue con la misma cant de chacras
        assertEquals(32, perEq.getBanco().getCantidadChacras());

        //////////////////////

        // puede construir si tiene todas las escrituras de una provincia
        jota.getEscrituras().add(tuc1); jota.getEscrituras().add(tuc2);
        perEq.getBanco().setCantidadChacras(32);
        perEq.construirMejora(tuc1,jota);

        assertEquals(7000,tuc1.getDuenio().getCuenta());
        assertEquals(1, tuc1.getCantChacras());
        //disminuyo en 1 la cant de chacras del banco
        assertEquals(31, perEq.getBanco().getCantidadChacras());

        //////////////////////

        //puede construir Estancias, si tiene el dinero y 4 chacras
        //NO tiene el dinero, ni las 4 chacras
        jota.setCuenta(100);
        perEq.getBanco().setCantidadEstancias(12);
        perEq.construirMejora(tuc1,jota);

        assertEquals(100,tuc1.getDuenio().getCuenta());
        assertEquals(0, tuc1.getCantEstancias());
        // cant de estancias del banco sigue igual
        assertEquals(12, perEq.getBanco().getCantidadEstancias());

        ///////////////////////////

        //NO tiene el dinero, pero SI tiene las 4 chacras
        jota.setCuenta(100);
        tuc1.setCantChacras(4);
        perEq.getBanco().setCantidadEstancias(12);
        perEq.construirMejora(tuc1,jota);
        assertEquals(100,tuc1.getDuenio().getCuenta());
        assertEquals(0, tuc1.getCantEstancias());
        // cant de estancias del banco sigue igual
        assertEquals(12, perEq.getBanco().getCantidadEstancias());

        ////////////////////////

        //SI tiene el dinero y SI tiene las 4 chacras
        jota.setCuenta(10000);
        tuc1.setCantChacras(4);
        perEq.getBanco().setCantidadEstancias(12);
        perEq.construirMejora(tuc1,jota);

        //se construyo una estancia
        assertEquals(7000,tuc1.getDuenio().getCuenta());
        assertEquals(1, tuc1.getCantEstancias());
        //cuando se construye una Estancia, se entregan las 4 Chacras como parte de pago
        assertEquals(0, tuc1.getCantChacras());
        // cant de estancias del banco disminuyo
        assertEquals(11, perEq.getBanco().getCantidadEstancias());
    }

    @Test
    void verificarCostoConstruccionEstancia() {
        when(escritura.getDuenio()).thenReturn(jugador);

        when(escritura.getCostoConstruccionChacra()).thenReturn(100);
        when(jugador.getCuenta()).thenReturn(250);
        when(banco.getCantidadPropiedades()).thenReturn(10);
        assertTrue(perfilEquilibrado.verificarCostoConstruccionEstancia(escritura));

        when(escritura.getCostoConstruccionChacra()).thenReturn(200);
        when(jugador.getCuenta()).thenReturn(200);
        when(banco.getCantidadPropiedades()).thenReturn(23);
        assertTrue(perfilEquilibrado.verificarCostoConstruccionEstancia(escritura));

        when(escritura.getCostoConstruccionChacra()).thenReturn(200);
        when(jugador.getCuenta()).thenReturn(300);
        when(banco.getCantidadPropiedades()).thenReturn(10);
        assertFalse(perfilEquilibrado.verificarCostoConstruccionEstancia(escritura));
    }

    @Test
    void comprarPropiedad() {
        PerfilEquilibrado perEq = new PerfilEquilibrado();

        Jugador jota = new Jugador();
        jota.setCuenta(10000);
        jota.setNombre("Bot Equilibrado");

        Escritura escritura = new Escritura();
        escritura.setValorEscritura(5000);
        escritura.setProvincia(Provincia.SANTA_FE);
        escritura.setZona(Zona.NORTE);

        //puede comprar pq le alcanza el dinero, le sobran 5000
        assertTrue(perEq.comprarPropiedad(escritura, jota));
        assertEquals(jota, escritura.getDuenio());
        assertEquals(1,perEq.getEscriturasAdquiridasStaFe());
        assertEquals(0,perEq.getEscriturasAdquiridasTucuman());
        assertEquals(0,perEq.getEscriturasAdquiridasMendoza());
        assertEquals(5000, jota.getCuenta());


        Servicio servicio = new Servicio();
        servicio.setValorServicio(3600);
        servicio.setTipoServicio(TipoServicio.FERROCARRIL);
        servicio.setCantFerrocarriles(0);
        jota.setCuenta(4600);
        //puede comprar pq le alcanza el dinero, le sobra 1000
        assertTrue(perEq.comprarPropiedad(servicio, jota));
        assertEquals(1000, jota.getCuenta());

        Escritura escritura2 = new Escritura();
        escritura2.setValorEscritura(6000);
        escritura2.setProvincia(Provincia.TUCUMAN);
        escritura2.setZona(Zona.SUR);
    }

    @Test
    void comprarPorPreferenciaPropiedadV2() {
        PerfilEquilibrado pe = new PerfilEquilibrado();
        Jugador j = new Jugador();
        j.setCuenta(10000);
        j.setNombre("Bot Equilibrado");

        Escritura escrituraPreferida1 = new Escritura();
        escrituraPreferida1.setValorEscritura(5000);
        escrituraPreferida1.setEstadoHipoteca(false);
        escrituraPreferida1.setProvincia(Provincia.TUCUMAN);
        escrituraPreferida1.setZona(Zona.SUR);
        Jugador duenio = new Jugador();
        escrituraPreferida1.setDuenio(duenio);
        escrituraPreferida1.getDuenio().setBot(false);

        Escritura escrituraPreferida2 = new Escritura();
        escrituraPreferida2.setValorEscritura(4000);
        escrituraPreferida2.setEstadoHipoteca(false);
        escrituraPreferida2.setProvincia(Provincia.MENDOZA);
        escrituraPreferida2.setZona(Zona.CENTRO);

        Escritura escrituraNoPreferida = new Escritura();
        escrituraNoPreferida.setValorEscritura(3000);
        escrituraNoPreferida.setEstadoHipoteca(false);
        escrituraNoPreferida.setProvincia(Provincia.SALTA);
        escrituraNoPreferida.setZona(Zona.SUR);

        Servicio servicioBarato = new Servicio();
        servicioBarato.setValorServicio(3600);
        servicioBarato.setTipoServicio(TipoServicio.FERROCARRIL);
        servicioBarato.setCantFerrocarriles(0);
        servicioBarato.setCantCompanias(0);

        Servicio servicioCaro = new Servicio();
        servicioCaro.setValorServicio(5000);
        servicioCaro.setTipoServicio(TipoServicio.INGENIO);
        servicioCaro.setCantFerrocarriles(0);
        servicioCaro.setCantCompanias(0);

        Servicio servicioConDuenio = new Servicio();
        servicioConDuenio.setValorServicio(3600);
        servicioConDuenio.setTipoServicio(TipoServicio.FERROCARRIL);
        servicioConDuenio.setCantFerrocarriles(0);
        servicioConDuenio.setCantCompanias(0);
        servicioConDuenio.setDuenio(jugador);

        //si tiene el dinero y la provincia esta dentro de las preferidas, pero ya tiene dueño, NO compra
        assertFalse(pe.comprarPorPreferenciaPropiedadV2(escrituraPreferida1, j));
        assertNotEquals(j, escrituraPreferida1.getDuenio());
        assertEquals(0, pe.getEscriturasAdquiridasMendoza());
        assertEquals(0, pe.getEscriturasAdquiridasTucuman());
        assertEquals(0, pe.getEscriturasAdquiridasStaFe());
        assertEquals(10000, j.getCuenta());

        //si tiene el dinero, no tiene dueño y la provincia esta dentro de las preferidas, SI compra
        assertTrue(pe.comprarPorPreferenciaPropiedadV2(escrituraPreferida2, j));
        assertEquals(j, escrituraPreferida2.getDuenio());
        assertEquals(1, pe.getEscriturasAdquiridasMendoza());
        assertEquals(0, pe.getEscriturasAdquiridasTucuman());
        assertEquals(0, pe.getEscriturasAdquiridasStaFe());
        assertEquals(6000, j.getCuenta());

        //no compra, la provincia NO esta dentro de las preferidas
        assertFalse(pe.comprarPorPreferenciaPropiedadV2(escrituraNoPreferida, j));
        assertNotEquals(j, escrituraNoPreferida.getDuenio());
        assertEquals(1, pe.getEscriturasAdquiridasMendoza());
        assertEquals(0, pe.getEscriturasAdquiridasTucuman());
        assertEquals(0, pe.getEscriturasAdquiridasStaFe());
        assertEquals(6000, j.getCuenta());

        //no compra, no le alcanza el dinero
        j.setCuenta(1000);
        assertFalse(pe.comprarPorPreferenciaPropiedadV2(escrituraPreferida1, j));
        assertNotEquals(j, escrituraNoPreferida.getDuenio());
        assertEquals(1000, j.getCuenta());

        //si compra, pq tiene el dinero y es un servicio
        j.setCuenta(10000);
        assertTrue(pe.comprarPorPreferenciaPropiedadV2(servicioBarato, j));
        assertEquals(j, servicioBarato.getDuenio());
        assertEquals(6400, j.getCuenta());

        //si compra, pq tiene el dinero y es un servicio
        assertTrue(pe.comprarPorPreferenciaPropiedadV2(servicioCaro, j));
        assertEquals(j, servicioCaro.getDuenio());
        assertEquals(1400, j.getCuenta());

        //no compra, ya tiene dueño el servicio
        j.setCuenta(10000);
        assertFalse(pe.comprarPorPreferenciaPropiedadV2(servicioConDuenio, j));
        assertNotEquals(j, servicioConDuenio.getDuenio());
        assertEquals(10000, j.getCuenta());
    }

    @Test
    void comprarFueraDePreferenciaPropiedad() {
        PerfilEquilibrado perEq = new PerfilEquilibrado();

        Jugador jota = new Jugador();
        jota.setCuenta(10000);
        jota.setNombre("Bot Equilibrado");

        Escritura escrituraNoPreferida1 = new Escritura();
        escrituraNoPreferida1.setValorEscritura(500);
        escrituraNoPreferida1.setEstadoHipoteca(false);
        escrituraNoPreferida1.setProvincia(Provincia.FORMOSA);
        escrituraNoPreferida1.setZona(Zona.CENTRO);

        Escritura escrituraNoPreferida2 = new Escritura();
        escrituraNoPreferida2.setValorEscritura(1000);
        escrituraNoPreferida2.setEstadoHipoteca(false);
        escrituraNoPreferida2.setProvincia(Provincia.SALTA);
        escrituraNoPreferida2.setZona(Zona.SUR);

        //NO se cumplen las condiciones (Cant de propiedades insuficiente) para comprar una escritura NO preferida
        perEq.setCantidadPropiedadesTotales(1);
        assertFalse(perEq.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida1, jota));

        //NO se cumplen las condiciones (Cant de propiedades insuficiente) para comprar una escritura NO preferida
        perEq.setCantidadPropiedadesTotales(2);
        assertFalse(perEq.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida2, jota));

        //NO se cumplen las condiciones (dinero insuficiente) para comprar una escritura NO preferida
        perEq.setCantidadPropiedadesTotales(3);
        jota.setCuenta(0);
        assertFalse(perEq.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida2, jota));
        assertEquals(0, jota.getCuenta());

        //SI se cumplen las condiciones para comprar una escritura NO preferida
        jota.setCuenta(10000);
        perEq.setCantidadPropiedadesTotales(3);
        assertTrue(perEq.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida2, jota));
        assertEquals(9000, jota.getCuenta());

        Servicio ferrocarril = new Servicio();
        ferrocarril.setValorServicio(3600);
        ferrocarril.setDuenio(null);
        ferrocarril.setTipoServicio(TipoServicio.FERROCARRIL);
        ferrocarril.setCantFerrocarriles(0);
        perEq.setCantidadPropiedadesTotales(3);
        jota.setCuenta(4600);
        //Si es un servicio, debe cumplir las mismas condiciones q para comprar una escritura (tener dinero y cant de propiedades)
        //cumple las condiciones, SI compra
        assertTrue(perEq.comprarFueraDePreferenciaPropiedad(ferrocarril, jota));
        assertEquals(1000, jota.getCuenta());

        //NO lo compra (dinero insuficiente)
        jota.setCuenta(1000);
        assertFalse(perEq.comprarFueraDePreferenciaPropiedad(ferrocarril, jota));
        assertEquals(1000, jota.getCuenta());

    }

    @Test
    void contarEscriturasAdquiridasPorZona(){
        PerfilEquilibrado perEq = new PerfilEquilibrado();

        Jugador jota = new Jugador();
        jota.setCuenta(10000);
        jota.setNombre("Bot Equilibrado");

        Escritura f = new Escritura();
        f.setProvincia(Provincia.FORMOSA);
        f.setZona(Zona.SUR);
        perEq.contarEscriturasAdquiridasPorZona(f);

        assertEquals(0, perEq.getEscriturasAdquiridasStaFe());
        assertEquals(0, perEq.getEscriturasAdquiridasMendoza());
        assertEquals(0, perEq.getEscriturasAdquiridasTucuman());

        Escritura sf = new Escritura();
        sf.setProvincia(Provincia.SANTA_FE);
        sf.setZona(Zona.SUR);
        perEq.contarEscriturasAdquiridasPorZona(sf);
        assertEquals(1, perEq.getEscriturasAdquiridasStaFe());
        assertEquals(0, perEq.getEscriturasAdquiridasMendoza());
        assertEquals(0, perEq.getEscriturasAdquiridasTucuman());

        Escritura m = new Escritura();
        m.setProvincia(Provincia.MENDOZA);
        m.setZona(Zona.CENTRO);
        perEq.contarEscriturasAdquiridasPorZona(m);
        assertEquals(1, perEq.getEscriturasAdquiridasStaFe());
        assertEquals(1, perEq.getEscriturasAdquiridasMendoza());
        assertEquals(0, perEq.getEscriturasAdquiridasTucuman());

        Escritura t = new Escritura();
        t.setProvincia(Provincia.TUCUMAN);
        t.setZona(Zona.NORTE);
        perEq.contarEscriturasAdquiridasPorZona(t);
        assertEquals(1, perEq.getEscriturasAdquiridasStaFe());
        assertEquals(1, perEq.getEscriturasAdquiridasMendoza());
        assertEquals(1, perEq.getEscriturasAdquiridasTucuman());

    }
    @Test
    void comprarConEquilibrio() {
        perfilEquilibrado.setCantidadPropiedadesTotales(5);
        perfilEquilibrado.setCantidadConstrucciones(3);
        assertTrue(perfilEquilibrado.comprarConEquilibrio());

        perfilEquilibrado.setCantidadPropiedadesTotales(3);
        perfilEquilibrado.setCantidadConstrucciones(5);
        assertTrue(perfilEquilibrado.comprarConEquilibrio());

        perfilEquilibrado.setCantidadPropiedadesTotales(1);
        perfilEquilibrado.setCantidadConstrucciones(7);
        assertFalse(perfilEquilibrado.comprarConEquilibrio());

        perfilEquilibrado.setCantidadPropiedadesTotales(7);
        perfilEquilibrado.setCantidadConstrucciones(1);
        assertFalse(perfilEquilibrado.comprarConEquilibrio());
    }
}