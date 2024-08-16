package com.example.demo.Model.Perfil;
import com.example.demo.Model.*;
import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Enums.TipoServicio;
import com.example.demo.Model.Enums.Zona;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
/**
 Perfil de jugador agresivo:
 a. Este jugador busca maximizar el retorno de inversión rápidamente,
 incluso a costa de correr mayores riesgos. (Provincias prioritarias:
 Tucuman, Córdoba y Buenos Aires)
 b. Este jugador buscará comprar la serie de Ferrocarriles y Companias.
 c. Este jugador no comprará fuera de las provincias de preferencia a no
 ser que ya se hayan vendido a otros jugadores al menos una propiedad
 de todas sus zonas de preferencia o el mismo haya completado sus
 zonas; en dicho caso, comprará tantas propiedades como pueda.
 d. Priorizará la expansión rápida y construirá mejoras cada vez que
 pueda.*/
@Data
@AllArgsConstructor
public class PerfilAgresivo implements Perfil {

    private List<Provincia> provinciasPreferidas;
        private int escriturasAdquiridasTucuman;
        private int escriturasAdquiridasCordoba;
        private int escriturasAdquiridasBsAs;
        private int cEscritPrefComprasPorOtro;
        private Banco banco;
    public PerfilAgresivo() {
        iniciarProvPreferidas();
        escriturasAdquiridasTucuman=0;
        escriturasAdquiridasCordoba=0;
        escriturasAdquiridasBsAs=0;
        this.banco=Banco.getBancoInstance();
        cEscritPrefComprasPorOtro=0;
    }
    @Override
    public boolean esPreferida(Provincia provincia) {
        return provinciasPreferidas.contains(provincia);
    }
    @Override
    public void iniciarProvPreferidas() {
        this.provinciasPreferidas = new ArrayList<>();
        provinciasPreferidas.add(Provincia.TUCUMAN);
        provinciasPreferidas.add(Provincia.CORDOBA);
        provinciasPreferidas.add(Provincia.BUENOS_AIRES);
    }

    @Override
    public <T extends Casillero> boolean comprarFueraDePreferenciaPropiedad(T casillero,Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            comprobarEscrituraPreferidaDuenio(escritura);
            if (!esPreferida(escritura.getProvincia())){
                if(cEscritPrefComprasPorOtro>1 && (escriturasAdquiridasBsAs==3|| escriturasAdquiridasCordoba==3||escriturasAdquiridasTucuman==2)){
                    if (escritura.getValorEscritura() <= jugador.getCuenta()) {
                        comprarPropiedad(escritura, jugador);
                        return true;
                    }
                }
            }else return false;
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if (servicio.getTipoServicio().equals(TipoServicio.FERROCARRIL)) {
                if (servicio.getDuenio() == null) {
                    if (servicio.getValorServicio() <= jugador.getCuenta()) {

                        comprarPropiedad(servicio, jugador);
                        return true;
                    }
                }
            } else if (servicio.getValorServicio() <= jugador.getCuenta()) {
                if (servicio.getDuenio() == null) {
                    comprarPropiedad(servicio, jugador);
                    return true;
                }
            }else return  false;
        }
        return false;
    }
    @Override
    public void construirMejora(Casillero casillero,Jugador jugador) {
        if (casillero instanceof Escritura){
            Escritura escritura = (Escritura) casillero;
            if (escritura.getCostoConstruccionChacra() <= jugador.getCuenta() && banco.getCantidadChacras()>0){
                if (escritura.getDuenio().getId()==jugador.getId()) {
                    if (escritura.ProvinciaCompleta(escritura)) {
                        //solo contruye Chacras cuando NO tiene Estancias y tiene menos de 4 Chacras
                        if (escritura.getCantChacras() <= 3 && escritura.getCantEstancias().equals(0)) {
                            escritura.setCantChacras(escritura.getCantChacras() + 1);
                            escritura.getDuenio().setCuenta(escritura.getDuenio().getCuenta() - escritura.getCostoConstruccionChacra());
                            banco.setCantidadChacras(banco.getCantidadChacras() - 1);

                            //solo construye Estancias cuando tiene 4 Chacras y ninguna Estancia
                        } else if (escritura.getCantChacras() == 4 && escritura.getCantEstancias().equals(0) && banco.getCantidadEstancias()>0) {
                            escritura.setCantEstancias(escritura.getCantEstancias() + 1);
                            escritura.getDuenio().setCuenta(escritura.getDuenio().getCuenta() - escritura.getCostoConstruccionEstancia());
                            //cuando se construye una Estancia, se deben devolver las 4 Chacras.
                            //Estas chacras cuentan como parte del pago, por eso en la Clase Escritura
                            // el costo de construccion de una Estancia cambio.
                            escritura.setCantChacras(0);
                            banco.setCantidadEstancias(banco.getCantidadEstancias() - 1);
                            banco.setCantidadChacras(banco.getCantidadChacras() + 4);
                        }
                    }
                }
            }
        }
    }


    @Override
    public <T extends Casillero> boolean comprarPropiedad(T casillero, Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            escritura.setDuenio(jugador);
            jugador.getEscrituras().add(escritura);
            escritura.getDuenio().setCuenta(jugador.getCuenta() - escritura.getValorEscritura());
            contarEscriturasAdquiridasPorZona(escritura);
            banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
            return true;
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            servicio.setDuenio(jugador);
            jugador.getServicios().add(servicio);
            if (servicio.getTipoServicio().equals(TipoServicio.FERROCARRIL)) {
                servicio.setCantFerrocarriles(servicio.getCantFerrocarriles() + 1);
                servicio.getDuenio().setCuenta(jugador.getCuenta() - servicio.getValorServicio());
                banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
                return true;
            } else servicio.setCantCompanias(servicio.getCantCompanias() + 1);
            servicio.getDuenio().setCuenta(jugador.getCuenta() - servicio.getValorServicio());
            banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
            return true;
        } else return false;
    }

    @Override
    public <T extends Casillero> boolean comprarPorPreferenciaPropiedadV2(T casillero, Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            comprobarEscrituraPreferidaDuenio(escritura);
            if (esPreferida(escritura.getProvincia())) {
                if (!escritura.isEstadoHipoteca() && escritura.getDuenio() == null) {
                    if (escritura.getValorEscritura() <= jugador.getCuenta()) {
                        comprarPropiedad(escritura, jugador);
                        return true;
                    }
                }
            }else  return false;
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;

            if (servicio.getDuenio() == null) {
                if (servicio.getValorServicio() <= jugador.getCuenta()) {
                    comprarPropiedad(servicio, jugador);
                    return true;
                }
            }else return false;
        }
        return false;
    }



    @Override
    public void contarEscriturasAdquiridasPorZona(Escritura escritura) {
        if (escritura.getProvincia().equals(Provincia.BUENOS_AIRES) && (escritura.getZona() != null)) {
            escriturasAdquiridasBsAs++;
        } else if (escritura.getProvincia().equals(Provincia.CORDOBA) && (escritura.getZona() != null)) {
            escriturasAdquiridasCordoba++;
        } else if (escritura.getProvincia().equals(Provincia.TUCUMAN) && (escritura.getZona() != null)) {
            escriturasAdquiridasTucuman++;
        }
    }

    public void comprobarEscrituraPreferidaDuenio(Escritura escritura){
        if (escritura.getDuenio()!=null && escritura.getDuenio().getBot().equals(false) && (escritura.getProvincia().equals(Provincia.BUENOS_AIRES) || escritura.getProvincia().equals(Provincia.CORDOBA) ||escritura.getProvincia().equals(Provincia.TUCUMAN))){
            cEscritPrefComprasPorOtro++;
        }
    }
}
