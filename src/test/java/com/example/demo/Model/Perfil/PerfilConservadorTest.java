package com.example.demo.Model.Perfil;
import com.example.demo.Model.Casillero;
import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Enums.TipoServicio;
import com.example.demo.Model.Enums.Zona;
import com.example.demo.Model.Jugador;
import com.example.demo.Model.Escritura;
import com.example.demo.Model.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


class PerfilConservadorTest {
    private PerfilConservador perfilConservador;
    private Jugador jugador;

    @BeforeEach
    void setUp() {
        perfilConservador = new PerfilConservador();
        jugador = new Jugador();
        jugador.setCuenta(10000);
    }

    @Test
    void esPreferida() {
        assertTrue(perfilConservador.esPreferida(Provincia.FORMOSA));
        assertTrue(perfilConservador.esPreferida(Provincia.RIO_NEGRO));
        assertTrue(perfilConservador.esPreferida(Provincia.SALTA));
        assertFalse(perfilConservador.esPreferida(Provincia.BUENOS_AIRES));
        assertFalse(perfilConservador.esPreferida(Provincia.CORDOBA));
    }

    @Test
    void iniciarProvPreferidas() {
        assertEquals(Arrays.asList(Provincia.FORMOSA, Provincia.RIO_NEGRO, Provincia.SALTA), perfilConservador.getProvinciasPreferidas());
        assertNotEquals(Arrays.asList(Provincia.CORDOBA, Provincia.BUENOS_AIRES, Provincia.TUCUMAN), perfilConservador.getProvinciasPreferidas());
    }

    @Test
    void comprarPropiedad() {
        Escritura escritura = new Escritura();
        escritura.setValorEscritura(5000);
        escritura.setProvincia(Provincia.SANTA_FE);
        escritura.setZona(Zona.NORTE);

        //puede comprar pq le alcanza el dinero, le sobran 5000
        assertTrue(perfilConservador.comprarPropiedad(escritura, jugador));
        assertEquals(jugador, escritura.getDuenio());
        assertEquals(1,perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(5000, jugador.getCuenta());


        Servicio servicio = new Servicio();
        servicio.setValorServicio(5000);
        servicio.setTipoServicio(TipoServicio.INGENIO);
        servicio.setCantFerrocarriles(0);
        //puede comprar pq le alcanza el dinero, le sobra 0
        assertTrue(perfilConservador.comprarPropiedad(servicio, jugador));
        assertEquals(2,perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(0, jugador.getCuenta());
    }


    @Test
    void comprarPorPreferenciaPropiedadV2() {

        Escritura escrituraPreferida1 = new Escritura();
        escrituraPreferida1.setValorEscritura(1000);
        escrituraPreferida1.setEstadoHipoteca(false);
        escrituraPreferida1.setProvincia(Provincia.FORMOSA);
        escrituraPreferida1.setZona(Zona.NORTE);

        Escritura escrituraPreferida2 = new Escritura();
        escrituraPreferida2.setValorEscritura(2000);
        escrituraPreferida2.setEstadoHipoteca(false);
        escrituraPreferida2.setProvincia(Provincia.RIO_NEGRO);
        escrituraPreferida2.setZona(Zona.SUR);

        Escritura escrituraNoPreferida = new Escritura();
        escrituraNoPreferida.setValorEscritura(6000);
        escrituraNoPreferida.setEstadoHipoteca(false);
        escrituraNoPreferida.setProvincia(Provincia.BUENOS_AIRES);
        escrituraNoPreferida.setZona(Zona.CENTRO);

        Servicio servicioBarato = new Servicio();
        servicioBarato.setValorServicio(3600);
        servicioBarato.setTipoServicio(TipoServicio.FERROCARRIL);

        Servicio servicioCaro = new Servicio();
        servicioCaro.setValorServicio(5000);
        servicioCaro.setTipoServicio(TipoServicio.INGENIO);

        //compra pq tiene el dinero y es de preferencia
        assertTrue(perfilConservador.comprarPorPreferenciaPropiedadV2(escrituraPreferida1, jugador));
        assertEquals(jugador, escrituraPreferida1.getDuenio());
        assertEquals(1, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(9000, jugador.getCuenta());

        //compra pq tiene el dinero y es de preferencia
        assertTrue(perfilConservador.comprarPorPreferenciaPropiedadV2(escrituraPreferida2, jugador));
        assertEquals(jugador, escrituraPreferida2.getDuenio());
        assertEquals(2, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(7000, jugador.getCuenta());

        //compra pq tiene el dinero y es el servicio mas barato
        assertTrue(perfilConservador.comprarPorPreferenciaPropiedadV2(servicioBarato, jugador));
        assertEquals(jugador, servicioBarato.getDuenio());
        assertEquals(3, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(3400, jugador.getCuenta());

        //NO compra pq tiene NO el dinero y NO es de preferencia
        jugador.setCuenta(0);
        assertFalse(perfilConservador.comprarPorPreferenciaPropiedadV2(escrituraNoPreferida, jugador));
        assertNotEquals(jugador, escrituraNoPreferida.getDuenio());
        assertEquals(3, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(0, jugador.getCuenta());

        //NO compra pq  NO es de preferencia
        jugador.setCuenta(10000);
        assertFalse(perfilConservador.comprarPorPreferenciaPropiedadV2(escrituraNoPreferida, jugador));
        assertNotEquals(jugador, escrituraNoPreferida.getDuenio());
        assertEquals(3, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(10000, jugador.getCuenta());

        //NO compra pq es el servicio mas caro
        assertFalse(perfilConservador.comprarPorPreferenciaPropiedadV2(servicioCaro, jugador));
        assertNotEquals(jugador, servicioCaro.getDuenio());
        assertEquals(3, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(10000, jugador.getCuenta());
    }

    @Test
    void construirMejora() {
        jugador.setNombre("Bot Conservador");
        jugador.setBot(true);

        Escritura sal1 = new Escritura();
        sal1.setDuenio(jugador);
        sal1.setCantChacras(0);
        sal1.setCantEstancias(0);
        sal1.setCostoConstruccionChacra(3000);
        sal1.setCostoConstruccionEstancia(sal1.getCostoConstruccionChacra()); // costoEstancia = costoChacra * 5
        sal1.setProvincia(Provincia.SALTA);
        sal1.setZona(Zona.NORTE);

        Escritura sal2 = new Escritura();
        sal2.setProvincia(Provincia.SALTA);
        sal2.setDuenio(jugador);
        sal2.setZona(Zona.SUR);

        Escritura sal3 = new Escritura();
        sal3.setProvincia(Provincia.SALTA);
        sal3.setDuenio(jugador);
        sal3.setZona(Zona.CENTRO);

        // si no tiene todas las escrituras de una provincia, no puede construir
        perfilConservador.getBanco().setCantidadChacras(32);
        perfilConservador.construirMejora(sal1,jugador);

        assertEquals(10000,sal1.getDuenio().getCuenta());
        assertEquals(0, sal1.getCantChacras());
        //banco sigue con la misma cant de chacras
        assertEquals(32, perfilConservador.getBanco().getCantidadChacras());

        ///////////////////

        // puede construir si tiene todas las escrituras de una provincia
        jugador.getEscrituras().add(sal1); jugador.getEscrituras().add(sal2); jugador.getEscrituras().add(sal3);
        perfilConservador.getBanco().setCantidadChacras(32);
        perfilConservador.construirMejora(sal1,jugador);

        assertEquals(7000,sal1.getDuenio().getCuenta());
        assertEquals(1, sal1.getCantChacras());
        //disminuyo en 1 la cant de chacras del banco
        assertEquals(31, perfilConservador.getBanco().getCantidadChacras());

        ///////////////////

        //puede construir Estancias, si tiene el dinero y 4 chacras
        //NO tiene el dinero, ni las 4 chacras
        jugador.setCuenta(100);
        perfilConservador.getBanco().setCantidadEstancias(12);
        perfilConservador.construirMejora(sal1,jugador);

        assertEquals(100,sal1.getDuenio().getCuenta());
        assertEquals(0, sal1.getCantEstancias());
        // cant de estancias del banco sigue igual
        assertEquals(12, perfilConservador.getBanco().getCantidadEstancias());

        ///////////////////////////

        //NO tiene el dinero, pero SI tiene las 4 chacras
        jugador.setCuenta(100);
        sal1.setCantChacras(4);
        perfilConservador.getBanco().setCantidadEstancias(12);
        perfilConservador.construirMejora(sal1,jugador);
        assertEquals(100,sal1.getDuenio().getCuenta());
        assertEquals(0, sal1.getCantEstancias());
        // cant de estancias del banco sigue igual
        assertEquals(12, perfilConservador.getBanco().getCantidadEstancias());

        ////////////////////////

        //SI tiene el dinero y SI tiene las 4 chacras
        jugador.setCuenta(10000);
        sal1.setCantChacras(4);
        perfilConservador.getBanco().setCantidadEstancias(12);
        perfilConservador.construirMejora(sal1,jugador);

        assertEquals(7000,sal1.getDuenio().getCuenta());
        assertEquals(1, sal1.getCantEstancias());
        //cuando se construye una Estancia, se entregan las 4 Chacras como parte de pago
        assertEquals(0, sal1.getCantChacras());
        // cant de estancias del banco disminuyo
        assertEquals(11, perfilConservador.getBanco().getCantidadEstancias());

    }

    @Test
    void comprarFueraDePreferenciaPropiedad() {
        Escritura escrituraPreferida = new Escritura();
        escrituraPreferida.setValorEscritura(1000);
        escrituraPreferida.setEstadoHipoteca(false);
        escrituraPreferida.setProvincia(Provincia.FORMOSA);
        escrituraPreferida.setZona(Zona.NORTE);

        Escritura escrituraNoPreferida1 = new Escritura();
        escrituraNoPreferida1.setValorEscritura(1500);
        escrituraNoPreferida1.setEstadoHipoteca(false);
        escrituraNoPreferida1.setProvincia(Provincia.BUENOS_AIRES);
        escrituraNoPreferida1.setZona(Zona.NORTE);

        Escritura escrituraNoPreferida2 = new Escritura();
        escrituraNoPreferida2.setValorEscritura(2000);
        escrituraNoPreferida2.setEstadoHipoteca(false);
        escrituraNoPreferida2.setProvincia(Provincia.CORDOBA);
        escrituraNoPreferida2.setZona(Zona.SUR);


        Servicio servicioMasBarato = new Servicio();
        servicioMasBarato.setValorServicio(3600);
        servicioMasBarato.setDuenio(null);
        servicioMasBarato.setTipoServicio(TipoServicio.FERROCARRIL);

        perfilConservador.setCantidadPropiedadesTotales(5);

        // Comprar una Escritura preferida
        assertFalse(perfilConservador.comprarFueraDePreferenciaPropiedad(escrituraPreferida, jugador));
        assertTrue(perfilConservador.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida1, jugador));
        assertEquals(6, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(8500, jugador.getCuenta());

        perfilConservador.setCantidadPropiedadesTotales(10);

        assertTrue(perfilConservador.comprarFueraDePreferenciaPropiedad(escrituraNoPreferida2, jugador));
        perfilConservador.setCantidadPropiedadesTotales(20);

        assertTrue(perfilConservador.comprarFueraDePreferenciaPropiedad(servicioMasBarato, jugador));
        assertEquals(21, perfilConservador.getCantidadPropiedadesTotales());
        assertEquals(servicioMasBarato.getDuenio().getCuenta(), jugador.getCuenta());
    }

    @Test
    void verificarCostoConstruccionEstancia() {
        Escritura escritura = new Escritura();
        escritura.setCostoConstruccionChacra(2000);
        escritura.setDuenio(jugador);
        boolean resultado = perfilConservador.verificarCostoConstruccionEstancia(escritura);
        assertTrue(resultado);
    }

    @Test
    void masBarata() {
        Escritura escrituraMil = new Escritura();
        escrituraMil.setValorEscritura(1000);

        Escritura escrituraMasCara = new Escritura();
        escrituraMasCara.setValorEscritura(4000);

        Escritura escrituraDosSeis = new Escritura();
        escrituraDosSeis.setValorEscritura(2600);

        Servicio servicioTresSeis = new Servicio();
        servicioTresSeis.setValorServicio(3600);

        assertTrue(perfilConservador.masBarata(escrituraMil));
        assertFalse(perfilConservador.masBarata(escrituraMasCara));
        assertTrue(perfilConservador.masBarata(escrituraDosSeis));
        assertTrue(perfilConservador.masBarata(servicioTresSeis));

    }

    @Test
    void contarEscriturasAdquiridasPorZonaTest(){
        Escritura forS = new Escritura();
        forS.setProvincia(Provincia.FORMOSA);
        forS.setZona(Zona.SUR);

        Escritura cbaN = new Escritura();
        cbaN.setProvincia(Provincia.CORDOBA);
        cbaN.setZona(Zona.NORTE);

        perfilConservador.contarEscriturasAdquiridasPorZona(forS);
        perfilConservador.contarEscriturasAdquiridasPorZona(cbaN);

        assertEquals(1, perfilConservador.getEscriturasAdquiridasFormosa());
        assertEquals(0, perfilConservador.getEscriturasAdquiridasSalta());
        assertEquals(0, perfilConservador.getEscriturasAdquiridasRioNegro());

    }
}