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
 Perfil de jugador equilibrado:
 a. Este jugador busca un equilibrio entre la acumulación de propiedades
 y la construcción de mejoras. (Provincias prioritarias: Mendoza, Santa
 Fe y Tucuman)
 b. Este jugador buscará comprar la serie de Ferrocarriles.
 c. Este jugador comprará fuera de las provincias de preferencia 1 de cada
 3 propiedades que compre.
 d. Construirá mejoras cuando el costo de la construcción no supere el
 50% de su dinero en cuenta o cuando se hayan vendido más del 75%
 de las propiedades.*/

@Data
@AllArgsConstructor
public class PerfilEquilibrado implements Perfil {

    private List<Provincia> provinciasPreferidas;
    private Banco banco;
    int cantidadPropiedadesTotales;
    int cantidadConstrucciones;

    private int  escriturasAdquiridasMendoza;
    private int  escriturasAdquiridasStaFe;
    private int  escriturasAdquiridasTucuman;



    public PerfilEquilibrado() {
        iniciarProvPreferidas();
        this.banco=Banco.getBancoInstance();
        cantidadPropiedadesTotales=0;
        cantidadConstrucciones=0;
        escriturasAdquiridasMendoza=0;
        escriturasAdquiridasStaFe=0;
        escriturasAdquiridasTucuman=0;
    }

    @Override
    public boolean esPreferida(Provincia provincia) {
        return provinciasPreferidas.contains(provincia);
    }

    @Override
    public void iniciarProvPreferidas() {
        this.provinciasPreferidas = new ArrayList<>();
        provinciasPreferidas.add(Provincia.MENDOZA);
        provinciasPreferidas.add(Provincia.SANTA_FE);
        provinciasPreferidas.add(Provincia.TUCUMAN);
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

    public boolean verificarCostoConstruccionEstancia(Escritura escritura) {
        double costoConstruccionChacra = escritura.getCostoConstruccionChacra();
        double cuenta = escritura.getDuenio().getCuenta();
        int cantidadPropiedades = banco.getCantidadPropiedades();
        double porcentajePropiedadesVendidas = (double) Math.ceil((cantidadPropiedades * 100.0) / 29);
        if (costoConstruccionChacra <= cuenta) {
            if (costoConstruccionChacra <= cuenta * 0.50 || porcentajePropiedadesVendidas > 75) {
                return true;

            } else return false;

        }else return false;
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
            cantidadPropiedadesTotales++;
            return true;
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            servicio.setDuenio(jugador);
            jugador.getServicios().add(servicio);
            servicio.actualizarCantServicios();
            servicio.getDuenio().setCuenta(jugador.getCuenta() - servicio.getValorServicio());
            servicio.actualizarCantServicios();
            banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
            cantidadPropiedadesTotales++;
            return true;
        } else
            return false;
    }

    @Override
    public <T extends Casillero> boolean comprarPorPreferenciaPropiedadV2(T casillero,Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            if (esPreferida(Provincia.valueOf(escritura.getProvincia().name())) && comprarConEquilibrio()) {
                if (!escritura.isEstadoHipoteca() && escritura.getDuenio() == null) {
                    if (escritura.getValorEscritura() <= jugador.getCuenta()) {
                        comprarPropiedad(escritura,jugador);
                        return true;
                    }
                }
            }
            else return false;
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if (servicio.getTipoServicio().equals(TipoServicio.FERROCARRIL)) {
                if (servicio.getDuenio() == null) {
                    if (servicio.getValorServicio() <= jugador.getCuenta()) {
                        comprarPropiedad(servicio,jugador);
                        return true;
                    }
                }
            }
            else if (comprarConEquilibrio() && servicio.getDuenio()==null ){
                comprarPropiedad(servicio,jugador);
                return true;
            }
            else return false;
        }
        return false;
    }

    @Override
    public <T extends Casillero> boolean comprarFueraDePreferenciaPropiedad(T casillero, Jugador jugador) {
        if (casillero instanceof Escritura) {
            Escritura escritura = (Escritura) casillero;
            if (!esPreferida(Provincia.valueOf(escritura.getProvincia().name())) && comprarConEquilibrio()) {
                if (cantidadPropiedadesTotales % 3 == 0 && cantidadPropiedadesTotales != 0) {
                    if (!escritura.isEstadoHipoteca() && escritura.getDuenio() == null) {
                        if (escritura.getValorEscritura() <= jugador.getCuenta()) {
                            comprarPropiedad(escritura,jugador);
                            return true;
                        }
                    }
                }
            }
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if (cantidadPropiedadesTotales % 3 == 0 && cantidadPropiedadesTotales != 0){
                comprarPropiedad(servicio,jugador);
                return true;
            }
        }
        return false;
    }

    @Override
    public void contarEscriturasAdquiridasPorZona(Escritura escritura) {
        if (escritura.getProvincia().equals(Provincia.MENDOZA) && (escritura.getZona() != null)){
            escriturasAdquiridasMendoza++;
        } else if (escritura.getProvincia().equals(Provincia.SANTA_FE) && (escritura.getZona() != null)){
            escriturasAdquiridasStaFe++;
        }else  if (escritura.getProvincia().equals(Provincia.TUCUMAN) && (escritura.getZona() != null)){
            escriturasAdquiridasTucuman++;
        }
    }

    public boolean comprarConEquilibrio() {
        double promedioPropiedadesConstrucciones = (cantidadPropiedadesTotales + cantidadConstrucciones) / 2.0;
        return Math.abs(cantidadPropiedadesTotales - promedioPropiedadesConstrucciones) <= 2
                || Math.abs(cantidadConstrucciones - promedioPropiedadesConstrucciones) <= 2;
    }
}

