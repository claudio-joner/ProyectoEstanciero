package com.example.demo.Model;

import com.example.demo.Model.Enums.TipoServicio;

import lombok.*;

import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio extends Casillero {
    // Atributos
    private Long id;
    private Integer valorServicio;
    private TipoServicio tipoServicio; //Enum TipoServicio
    private Integer cantFerrocarriles;
    private Integer cantCompanias;
    private Jugador duenio;


    // Constructores
    public Servicio(Integer posicion, String descripcion, TipoServicio tipoServicio) {
        super(posicion, descripcion);
        this.tipoServicio = tipoServicio;

        if (tipoServicio.equals(TipoServicio.PETROLERA)){
            this.valorServicio = 3800;
        } else if (tipoServicio.equals(TipoServicio.INGENIO)){
            this.valorServicio = 5000;
        } else { // los 4 Ferrocarriles y Bodega cuestan 3600
            this.valorServicio = 3600;
        }

        this.cantFerrocarriles = 0;
        this.cantCompanias = 0;
        this.duenio = null;
    }
    //cuenta cuantos Servicios hay de cada Tipo, FERROCARRIL o COMPANIA
    // (Compania puede ser Petrolera, Ingenio o Bodega)
    public void actualizarCantServicios(){
        int cantF = 0, cantC = 0;

        if(!(this.duenio.getServicios() == null))
        {
            for (Servicio s : this.duenio.getServicios()){
                if (s.tipoServicio.equals(TipoServicio.FERROCARRIL)){
                    cantF++;
                } else {
                    cantC++;
                }
            }
        }
        this.cantCompanias = cantC;
        this.cantFerrocarriles = cantF;
    }

    // Métodos

    //el costo de alquiler de las COMPANIAS (osea Petrolera, Ingenio o Bodega)
    // se determina por el valor de los Dados, multiplicado por la cant. de COMPANIAS del dueño
    public Integer cobrarAlquiler(Jugador jugador, int resultadoDados) {
        this.actualizarCantServicios();
        Integer costoAlquiler;

        if (this.tipoServicio.equals(TipoServicio.FERROCARRIL)){
            costoAlquiler = switch (cantFerrocarriles) {
                case 1 -> 500;
                case 2 -> 1000;
                case 3 -> 2000;
                case 4 -> 4000;
                default -> 0;
            };

        } else {
            costoAlquiler = cantCompanias * (resultadoDados * 100);
        }

        if (this.duenio != jugador){ //asi no le cobra alquiler al dueño xd
            jugador.setCuenta(jugador.getCuenta()-costoAlquiler);
            this.duenio.setCuenta(this.duenio.getCuenta()+costoAlquiler);
        }

        return costoAlquiler;
    }

    //devuelve el valor total de si mismo (el Servicio)
    // sirve para calcular el Patrimonio del Jugador
    public Integer ValorTotal(){

        return this.getValorServicio();
    }

    //Atributos necesarios:
    // Integer posicion, String descripcion, Integer valorVenta, TipoServicio tipoServicio
    public ArrayList<Servicio> InicializarServicios(){
        Servicio p = new Servicio(8, "Compañía Petrolera", TipoServicio.PETROLERA);
        Servicio b = new Servicio(16, "Compañía Bodega", TipoServicio.BODEGA);
        Servicio i = new Servicio(31, "Compañía Ingenio", TipoServicio.INGENIO);
        Servicio f1 = new Servicio(12, "Ferrocarril Gral. Belgrano", TipoServicio.FERROCARRIL);
        Servicio f2 = new Servicio(18, "Ferrocarril Gral. S. Martín", TipoServicio.FERROCARRIL);
        Servicio f3 = new Servicio(22, "Ferrocarril Gral. Mitre", TipoServicio.FERROCARRIL);
        Servicio f4 = new Servicio(27, "Ferrocarril Gral. Urquiza", TipoServicio.FERROCARRIL);

        ArrayList<Servicio> listServicios = new ArrayList<>();
        listServicios.add(p);
        listServicios.add(b);
        listServicios.add(i);
        listServicios.add(f1);
        listServicios.add(f2);
        listServicios.add(f3);
        listServicios.add(f4);

        return listServicios;
    }

}
