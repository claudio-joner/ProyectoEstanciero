package com.example.demo.Model.Perfil;

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

class PerfilAgresivoTest {
    private PerfilAgresivo perfilAgresivo;
    private Jugador jugador;

    @BeforeEach
    void setUp() {
        perfilAgresivo = new PerfilAgresivo();
        jugador = new Jugador();
        jugador.setCuenta(10000);
    }

    @Test
    void esPreferida() {
        assertTrue(perfilAgresivo.esPreferida(Provincia.TUCUMAN));
        assertTrue(perfilAgresivo.esPreferida(Provincia.CORDOBA));
        assertTrue(perfilAgresivo.esPreferida(Provincia.BUENOS_AIRES));
        assertFalse(perfilAgresivo.esPreferida(Provincia.SALTA));

    }

    @Test
    void iniciarProvPreferidas() {
        assertEquals(Arrays.asList(Provincia.TUCUMAN, Provincia.CORDOBA, Provincia.BUENOS_AIRES), perfilAgresivo.getProvinciasPreferidas());
        assertNotEquals(Arrays.asList(Provincia.FORMOSA, Provincia.RIO_NEGRO, Provincia.SALTA), perfilAgresivo.getProvinciasPreferidas());
    }
    @Test
    void comprarFueraDePreferenciaPropiedad(){

        Escritura escrituraNoPreferida1 = new Escritura();
        escrituraNoPreferida1.setValorEscritura(500);
        escrituraNoPreferida1.setEstadoHipoteca(false);
        escrituraNoPreferida1.setProvincia(Provincia.FORMOSA);
        escrituraNoPreferida1.setZona(Zona.CENTRO);

        Escritura escrituraNoPreferida2 = new Escritura();
        escrituraNoPreferida2.setValorEscritura(1000);
        escrituraNoPreferida2.setEstadoHipoteca(false);
        escrituraNoPreferida2.setProvincia(Provincia.SANTA_FE);
        escrituraNoPreferida2.setZona(Zona.SUR);

        //NO se cumplen las condiciones para comprar una escritura NO preferida
        perfilAgresivo.setCEscritPrefComprasPorOtro(1);
        assertFalse(perfilAgresivo.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida1, jugador));

        //NO se cumplen las condiciones para comprar una escritura NO preferida
        perfilAgresivo.setCEscritPrefComprasPorOtro(2);
        perfilAgresivo.setEscriturasAdquiridasBsAs(2);
        assertFalse(perfilAgresivo.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida2, jugador));

        //SI se cumplen las condiciones para comprar una escritura NO preferida
        perfilAgresivo.setEscriturasAdquiridasBsAs(3);
        assertTrue(perfilAgresivo.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida2, jugador));
        assertEquals(9000, jugador.getCuenta());

        Servicio ferrocarril = new Servicio();
        ferrocarril.setValorServicio(3600);
        ferrocarril.setDuenio(null);
        ferrocarril.setTipoServicio(TipoServicio.FERROCARRIL);
        ferrocarril.setCantFerrocarriles(0);
        //Si es un servicio, lo compra si o si
        assertTrue(perfilAgresivo.comprarFueraDePreferenciaPropiedad(ferrocarril, jugador));

        //Si es un servicio, lo compra si o si, excepto q no le alcance el dinero
        jugador.setCuenta(1000);
        assertFalse(perfilAgresivo.comprarFueraDePreferenciaPropiedad(ferrocarril, jugador));
    }

    @Test
    void construirMejora() {
        jugador.setNombre("Bot Agresivo");
        jugador.setBot(true);

        Escritura cba1 = new Escritura();
        cba1.setDuenio(jugador);
        cba1.setCantChacras(0);
        cba1.setCantEstancias(0);
        cba1.setCostoConstruccionChacra(3000);
        cba1.setCostoConstruccionEstancia(cba1.getCostoConstruccionChacra()); // costoEstancia = costoChacra * 5
        cba1.setProvincia(Provincia.CORDOBA);
        cba1.setZona(Zona.NORTE);

        Escritura cba2 = new Escritura();
        cba2.setProvincia(Provincia.CORDOBA);
        cba2.setDuenio(jugador);
        cba2.setZona(Zona.SUR);

        Escritura cba3 = new Escritura();
        cba3.setProvincia(Provincia.CORDOBA);
        cba3.setDuenio(jugador);
        cba3.setZona(Zona.CENTRO);

        // si no tiene todas las escrituras de una provincia, no puede construir
        perfilAgresivo.getBanco().setCantidadChacras(32);
        perfilAgresivo.construirMejora(cba1,jugador);

        assertEquals(10000,cba1.getDuenio().getCuenta());
        assertEquals(0, cba1.getCantChacras());
        //banco sigue con la misma cant de chacras
        assertEquals(32, perfilAgresivo.getBanco().getCantidadChacras());

        ///////////////////

        // puede construir si tiene todas las escrituras de una provincia
        jugador.getEscrituras().add(cba1); jugador.getEscrituras().add(cba2); jugador.getEscrituras().add(cba3);
        perfilAgresivo.getBanco().setCantidadChacras(32);
        perfilAgresivo.construirMejora(cba1,jugador);

        assertEquals(7000,cba1.getDuenio().getCuenta());
        assertEquals(1, cba1.getCantChacras());
        //disminuyo en 1 la cant de chacras del banco
        assertEquals(31, perfilAgresivo.getBanco().getCantidadChacras());

        ///////////////////

        //puede construir Estancias, si tiene el dinero y 4 chacras
        //NO tiene el dinero, ni las 4 chacras
        jugador.setCuenta(100);
        perfilAgresivo.getBanco().setCantidadEstancias(12);
        perfilAgresivo.construirMejora(cba1,jugador);

        assertEquals(100,cba1.getDuenio().getCuenta());
        assertEquals(0, cba1.getCantEstancias());
        // cant de estancias del banco sigue igual
        assertEquals(12, perfilAgresivo.getBanco().getCantidadEstancias());

        ///////////////////////////

        //NO tiene el dinero, pero SI tiene las 4 chacras
        jugador.setCuenta(100);
        cba1.setCantChacras(4);
        perfilAgresivo.getBanco().setCantidadEstancias(12);
        perfilAgresivo.construirMejora(cba1,jugador);
        assertEquals(100,cba1.getDuenio().getCuenta());
        assertEquals(0, cba1.getCantEstancias());
        // cant de estancias del banco sigue igual
        assertEquals(12, perfilAgresivo.getBanco().getCantidadEstancias());

        ////////////////////////

        //SI tiene el dinero y SI tiene las 4 chacras
        jugador.setCuenta(10000);
        cba1.setCantChacras(4);
        perfilAgresivo.getBanco().setCantidadEstancias(12);
        perfilAgresivo.construirMejora(cba1,jugador);

        assertEquals(7000,cba1.getDuenio().getCuenta());
        assertEquals(1, cba1.getCantEstancias());
        //cuando se construye una Estancia, se entregan las 4 Chacras como parte de pago
        assertEquals(0, cba1.getCantChacras());
        // cant de estancias del banco disminuyo
        assertEquals(11, perfilAgresivo.getBanco().getCantidadEstancias());
    }

    @Test
    void comprarPropiedad() {
        Escritura escritura = new Escritura();
        escritura.setValorEscritura(7000);
        escritura.setProvincia(Provincia.BUENOS_AIRES);
        escritura.setZona(Zona.SUR);

        //puede comprar pq le alcanza el dinero, le sobran 3000
        assertTrue(perfilAgresivo.comprarPropiedad(escritura, jugador));
        assertEquals(jugador, escritura.getDuenio());
        assertEquals(1,perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(0,perfilAgresivo.getEscriturasAdquiridasTucuman());
        assertEquals(0,perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(3000, jugador.getCuenta());


        Servicio servicio = new Servicio();
        servicio.setValorServicio(3600);
        servicio.setTipoServicio(TipoServicio.FERROCARRIL);
        servicio.setCantFerrocarriles(0);
        jugador.setCuenta(4600);
        //puede comprar pq le alcanza el dinero, le sobra 1000
        assertTrue(perfilAgresivo.comprarPropiedad(servicio, jugador));
        assertEquals(1000, jugador.getCuenta());
    }

    @Test
    void comprarPorPreferenciaPropiedadV2() {
        Escritura escrituraPreferida1 = new Escritura();
        escrituraPreferida1.setValorEscritura(4000);
        escrituraPreferida1.setEstadoHipoteca(false);
        escrituraPreferida1.setProvincia(Provincia.TUCUMAN);
        escrituraPreferida1.setZona(Zona.SUR);
        Jugador j = new Jugador();
        escrituraPreferida1.setDuenio(j);
        escrituraPreferida1.getDuenio().setBot(false);

        Escritura escrituraPreferida2 = new Escritura();
        escrituraPreferida2.setValorEscritura(5000);
        escrituraPreferida2.setEstadoHipoteca(false);
        escrituraPreferida2.setProvincia(Provincia.CORDOBA);
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

        //si tiene el dinero y la provincia esta dentro de las preferidas, pero ya tiene dueño
        assertFalse(perfilAgresivo.comprarPorPreferenciaPropiedadV2(escrituraPreferida1, jugador));
        assertNotEquals(jugador, escrituraPreferida1.getDuenio());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasTucuman());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(10000, jugador.getCuenta());

        //si tiene el dinero, no tiene dueño y la provincia esta dentro de las preferidas, compra
        assertTrue(perfilAgresivo.comprarPorPreferenciaPropiedadV2(escrituraPreferida2, jugador));
        assertEquals(jugador, escrituraPreferida2.getDuenio());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasTucuman());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(5000, jugador.getCuenta());

        //no compra, la provincia NO esta dentro de las preferidas
        assertFalse(perfilAgresivo.comprarPorPreferenciaPropiedadV2(escrituraNoPreferida, jugador));
        assertNotEquals(jugador, escrituraNoPreferida.getDuenio());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasTucuman());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(5000, jugador.getCuenta());

        //no compra, no le alcanza el dinero
        jugador.setCuenta(1000);
        assertFalse(perfilAgresivo.comprarPorPreferenciaPropiedadV2(escrituraPreferida1, jugador));
        assertNotEquals(jugador, escrituraNoPreferida.getDuenio());
        assertEquals(1000, jugador.getCuenta());

        //si compra, pq tiene el dinero y es un servicio
        jugador.setCuenta(10000);
        assertTrue(perfilAgresivo.comprarPorPreferenciaPropiedadV2(servicioBarato, jugador));
        assertEquals(jugador, servicioBarato.getDuenio());
        assertEquals(6400, jugador.getCuenta());

        //si compra, pq tiene el dinero y es un servicio
        assertTrue(perfilAgresivo.comprarPorPreferenciaPropiedadV2(servicioCaro, jugador));
        assertEquals(jugador, servicioCaro.getDuenio());
        assertEquals(1400, jugador.getCuenta());
    }

    @Test
    void contarEscriturasAdquiridasPorZona(){
        Escritura f = new Escritura();
        f.setProvincia(Provincia.FORMOSA);
        f.setZona(Zona.SUR);
        perfilAgresivo.contarEscriturasAdquiridasPorZona(f);

        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasTucuman());

        Escritura c = new Escritura();
        c.setProvincia(Provincia.CORDOBA);
        c.setZona(Zona.SUR);
        perfilAgresivo.contarEscriturasAdquiridasPorZona(c);
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasTucuman());

        Escritura b = new Escritura();
        c.setProvincia(Provincia.BUENOS_AIRES);
        c.setZona(Zona.CENTRO);
        perfilAgresivo.contarEscriturasAdquiridasPorZona(c);
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(0, perfilAgresivo.getEscriturasAdquiridasTucuman());

        Escritura t = new Escritura();
        c.setProvincia(Provincia.TUCUMAN);
        c.setZona(Zona.NORTE);
        perfilAgresivo.contarEscriturasAdquiridasPorZona(c);
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasCordoba());
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasBsAs());
        assertEquals(1, perfilAgresivo.getEscriturasAdquiridasTucuman());
    }

    @Test
    void comprobarEscrituraPreferidaDuenio(){
        Escritura sinDuenioNoPref = new Escritura();
        sinDuenioNoPref.setProvincia(Provincia.FORMOSA);

        Escritura sinDuenioPref = new Escritura();
        sinDuenioNoPref.setProvincia(Provincia.TUCUMAN);

        Escritura conDuenioNoPref = new Escritura();
        Jugador j = new Jugador();
        conDuenioNoPref.setDuenio(j);
        conDuenioNoPref.getDuenio().setBot(false);
        conDuenioNoPref.setProvincia(Provincia.FORMOSA);

        Escritura conDuenioPref = new Escritura();
        conDuenioPref.setDuenio(j);
        conDuenioPref.getDuenio().setBot(false);
        conDuenioPref.setProvincia(Provincia.CORDOBA);

        //la escritura NO tiene dueño y NO es preferida, no se suma al contador
        perfilAgresivo.comprobarEscrituraPreferidaDuenio(sinDuenioNoPref);
        assertEquals(0, perfilAgresivo.getCEscritPrefComprasPorOtro());

        //la escritura NO tiene dueño, pero SI es preferida, no se suma al contador
        perfilAgresivo.comprobarEscrituraPreferidaDuenio(sinDuenioPref);
        assertEquals(0, perfilAgresivo.getCEscritPrefComprasPorOtro());

        //la escritura SI tiene dueño, pero NO es preferida, no se suma al contador
        perfilAgresivo.comprobarEscrituraPreferidaDuenio(conDuenioNoPref);
        assertEquals(0, perfilAgresivo.getCEscritPrefComprasPorOtro());

        //la escritura SI tiene dueño y SI es preferida, SI se suma al contador
        perfilAgresivo.comprobarEscrituraPreferidaDuenio(conDuenioPref);
        assertEquals(1, perfilAgresivo.getCEscritPrefComprasPorOtro());


    }
}