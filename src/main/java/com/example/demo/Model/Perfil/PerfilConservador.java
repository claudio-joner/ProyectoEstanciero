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
 Perfil de jugador conservador:
 a. Este jugador tiende a ser cauteloso y prioriza la acumulación de
 propiedades de bajo costo. (Provincias prioritarias: Formosa, Río Negro
 y Salta)
 b. Este jugador comprará fuera de las provincias de preferencia 1 de cada
 5 propiedades que compre.
 c. Construirá mejoras solo cuando el costo de la construcción no
 sobrepase el 30% de su dinero en cuenta.*/
@Data
@AllArgsConstructor
public class PerfilConservador implements Perfil {
    int cantidadPropiedadesTotales;
    private List<Provincia> provinciasPreferidas;
    private Banco banco;
    private int  escriturasAdquiridasFormosa;
    private int  escriturasAdquiridasRioNegro;
    private int  escriturasAdquiridasSalta;


    public PerfilConservador() {
        iniciarProvPreferidas();
        cantidadPropiedadesTotales=0;
        this.escriturasAdquiridasFormosa=0;
        this.escriturasAdquiridasRioNegro=0;
        this.escriturasAdquiridasSalta=0;
        this.banco=Banco.getBancoInstance();
    }

    @Override
    public boolean esPreferida(Provincia provincia) {
     return provinciasPreferidas.contains(provincia);
    }

    @Override
    public void iniciarProvPreferidas() {
        this.provinciasPreferidas = new ArrayList<>();
        provinciasPreferidas.add(Provincia.FORMOSA);
        provinciasPreferidas.add(Provincia.RIO_NEGRO);
        provinciasPreferidas.add(Provincia.SALTA);
    }

    @Override
    public <T extends Casillero> boolean comprarPropiedad(T casillero, Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            escritura.setDuenio(jugador);
            jugador.getEscrituras().add(escritura);
            escritura.getDuenio().setCuenta(jugador.getCuenta() - escritura.getValorEscritura());
            banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
            cantidadPropiedadesTotales++;
            contarEscriturasAdquiridasPorZona(escritura);
            return true;
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            servicio.setDuenio(jugador);
            jugador.getServicios().add(servicio);
            if (servicio.getTipoServicio().equals(TipoServicio.FERROCARRIL)) {
                servicio.getDuenio().setCuenta(jugador.getCuenta() - servicio.getValorServicio());
                servicio.setCantFerrocarriles(+1);
                banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
                cantidadPropiedadesTotales++;
                return true;
            } else servicio.setCantCompanias(+1);
            banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
            jugador.setCuenta(jugador.getCuenta() - servicio.getValorServicio());
            cantidadPropiedadesTotales++;
            return true;
        } else
            return false;
    }
    @Override
    public <T extends Casillero> boolean comprarPorPreferenciaPropiedadV2(T casillero,Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            if (esPreferida(Provincia.valueOf(escritura.getProvincia().name())) && masBarata(escritura)) {
                if (!escritura.isEstadoHipoteca() && escritura.getDuenio() == null) {
                    if (escritura.getValorEscritura() <= jugador.getCuenta()) {
                        comprarPropiedad(escritura, jugador);
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if (masBarata(servicio)) {
                if (servicio.getDuenio() == null) {
                    if (servicio.getValorServicio() <=jugador.getCuenta()) {
                        comprarPropiedad(servicio,jugador);
                        return true;
                    }
                }
            }else {
                return false;
            }
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
    public <T extends Casillero> boolean comprarFueraDePreferenciaPropiedad(T casillero,Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            if (cantidadPropiedadesTotales % 5 == 0) {
                if (!esPreferida(Provincia.valueOf(escritura.getProvincia().name())) && !escritura.isEstadoHipoteca()) {
                    if (escritura.getDuenio() == null && jugador.getCuenta() >=  escritura.getValorEscritura()) {
                        comprarPropiedad(escritura,jugador);
                        return true;
                    }
                }else {
                    return false;
                }
            }
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if (cantidadPropiedadesTotales % 5 == 0) {
                if (masBarata(servicio)) {
                    if (servicio.getDuenio() == null) {
                        if (servicio.getValorServicio() <= jugador.getCuenta()) {
                            comprarPropiedad(servicio,jugador);
                            return true;
                        }
                    }
                    else return false;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }


    public boolean verificarCostoConstruccionEstancia(Escritura escritura) {
        if (escritura.getCostoConstruccionChacra()<= escritura.getDuenio().getCuenta() * 0.30){
            return true;
        }
        return false;
    }

    public <T extends Casillero> boolean masBarata(T casillero) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            if (escritura.getValorEscritura()==1000) {
                return true;
            }
            if (escritura.getValorEscritura()==1200) {
                return true;
            }
            if (escritura.getValorEscritura()==2000){
                return  true;
            }
            if (escritura.getValorEscritura()==2200){
                return  true;
            }
            if (escritura.getValorEscritura()==2600){
                return  true;
            }
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if (servicio.getValorServicio()==3600) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void  contarEscriturasAdquiridasPorZona(Escritura escritura){
        if (escritura.getProvincia().equals(Provincia.FORMOSA) && (escritura.getZona() != null)){
            escriturasAdquiridasFormosa++;
        } else if (escritura.getProvincia().equals(Provincia.RIO_NEGRO) && (escritura.getZona() != null)) {
            escriturasAdquiridasRioNegro++;
        }else if (escritura.getProvincia().equals(Provincia.SALTA) && (escritura.getZona() != null)) {
            escriturasAdquiridasSalta++;
        }
    }
}
