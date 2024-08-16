package com.example.demo.Model;

import com.example.demo.Model.Enums.Provincia;
import com.example.demo.Model.Enums.Zona;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Escritura extends Casillero {

    //atributos
    private Integer id;
    private Provincia provincia;
    private Zona zona;
    private Integer valorEscritura;

    //Alquiler segun cant. de x (NO hay formula para calcular el alquiler, estuve buscandole la logica pero no hay correlacion)
    private Integer alqCampoSolo;
    private Integer alq1Chacra;
    private Integer alq2Chacra;
    private Integer alq3Chacra;
    private Integer alq4Chacra;
    private Integer alq1Estancia;

    //siempre 1 Estancia = Chacra * 5
    private Integer costoConstruccionChacra;
    private Integer costoConstruccionEstancia;

    private Integer valorHipoteca;
    private boolean estadoHipoteca;

    private Integer cantChacras;
    private Integer cantEstancias;

    private Jugador duenio;

    //constructores
    public Escritura(Integer posicion, String descripcion,
                     Provincia provincia, Zona zona, Integer valorEscritura,
                     Integer alqCampoSolo, Integer alq1Chacra, Integer alq2Chacra,
                     Integer alq3Chacra, Integer alq4Chacra, Integer alq1Estancia,
                     Integer costoConstruccionChacra){
        super(posicion, descripcion);
        this.provincia = provincia;
        this.zona = zona;
        this.valorEscritura = valorEscritura;

        this.alqCampoSolo = alqCampoSolo;
        this.alq1Chacra = alq1Chacra;
        this.alq2Chacra = alq2Chacra;
        this.alq3Chacra = alq3Chacra;
        this.alq4Chacra = alq4Chacra;
        this.alq1Estancia = alq1Estancia;

        this.costoConstruccionChacra = costoConstruccionChacra;
        this.costoConstruccionEstancia = costoConstruccionChacra; //Es asi NO CAMBIAR!!!!!!

        this.valorHipoteca = 0;
        this.estadoHipoteca = false;

        this.cantChacras = 0;
        this.cantEstancias = 0;

        this.duenio = null;
    }


    //metodos

    // ATRIBUTOS NECESARIOS:
    // Integer posicion, String descripcion,
    //  Provincia provincia, Zona zona, valorEscritura,  alqCampoSolo,
    //  alq1Chacra,  alq2Chacra,  alq3Chacra, alq4Chacra,  alq1Estancia, costoConstruccionChacra
    public ArrayList<Escritura> InicializarEscritura(){
        ArrayList<Escritura> listEscrituras = new ArrayList<>();
        //Formosa SUR / CENTRO / NORTE
        Escritura forS = new Escritura(1, "Formosa Sur",
                Provincia.FORMOSA, Zona.SUR, 1000, 40, 200,
                600, 1700, 3000, 4750, 1000);
        Escritura forC = new Escritura(2, "Formosa Centro",
                Provincia.FORMOSA, Zona.CENTRO, 1000, 40, 200,
                600, 1700, 3000, 4750, 1000);
        Escritura forN = new Escritura(3, "Formosa Norte",
                Provincia.FORMOSA, Zona.NORTE, 1200, 80, 400,
                800, 3400, 6000, 9500, 1000);
        listEscrituras.add(forS); listEscrituras.add(forC); listEscrituras.add(forN);

        //Rio Negro SUR / NORTE
        Escritura rnS = new Escritura(5, "Río Negro Sur",
                Provincia.RIO_NEGRO, Zona.SUR, 2000, 110, 570,
                1700, 5150, 7600, 9500, 1000);
        Escritura rnN = new Escritura(6, "Río Negro Norte",
                Provincia.RIO_NEGRO, Zona.NORTE, 2200, 150, 750,
                2000, 5700, 8500, 11500, 1000);
        listEscrituras.add(rnS); listEscrituras.add(rnN);

        //Salta SUR / CENTRO / NORTE
        Escritura salS = new Escritura(9, "Salta Sur",
                Provincia.SALTA, Zona.SUR, 2600, 200, 1000,
                2800, 8500, 12000, 14200, 1500);
        Escritura salC = new Escritura(11, "Salta Centro",
                Provincia.SALTA, Zona.CENTRO, 2600, 200, 1000,
                2800, 8500, 12000, 14200, 1500);
        Escritura salN = new Escritura(13, "Salta Norte",
                Provincia.SALTA, Zona.NORTE, 3000, 230, 1150,
                3400, 9500, 13000, 17000, 1500);
        listEscrituras.add(salS); listEscrituras.add(salC); listEscrituras.add(salN);

        //Mendoza SUR / CENTRO / NORTE
        Escritura menS = new Escritura(17, "Mendoza Sur",
                Provincia.MENDOZA, Zona.SUR, 3400, 250, 1350,
                3800, 10500, 14200, 18000, 2000);
        Escritura menC = new Escritura(19, "Mendoza Centro",
                Provincia.MENDOZA, Zona.CENTRO, 3400, 250, 1350,
                3800, 10500, 14200, 18000, 2000);
        Escritura menN = new Escritura(20, "Mendoza Norte",
                Provincia.MENDOZA, Zona.NORTE, 3800, 300, 1500,
                4200, 11500, 15000, 19000, 2000);
        listEscrituras.add(menS); listEscrituras.add(menC); listEscrituras.add(menN);

        //Santa Fe SUR / CENTRO / NORTE
        Escritura sfeS = new Escritura(23, "Santa Fé Sur",
                Provincia.SANTA_FE, Zona.SUR, 4200, 350, 1700,
                4750, 13000, 16000, 20000, 2500);
        Escritura sfeC = new Escritura(24, "Santa Fé Centro",
                Provincia.SANTA_FE, Zona.CENTRO, 4200, 350, 1700,
                4750, 13000, 16000, 20000, 2500);
        Escritura sfeN = new Escritura(26, "Santa Fé Norte",
                Provincia.SANTA_FE, Zona.NORTE, 4600, 400, 2000,
                5750, 14000, 17000, 21000, 2500);
        listEscrituras.add(sfeS); listEscrituras.add(sfeC); listEscrituras.add(sfeN);

        //Tucuman SUR / NORTE
        Escritura tucS = new Escritura(29, "Tucumán Sur",
                Provincia.TUCUMAN, Zona.SUR, 5000, 400, 2000,
                5750, 14000, 17000, 21000, 2500);
        Escritura tucN = new Escritura(30, "Tucumán Norte",
                Provincia.TUCUMAN, Zona.NORTE, 5400, 450, 2400,
                6800, 16000, 19500, 23000, 3000);
        listEscrituras.add(tucS); listEscrituras.add(tucN);

        //Cordoba SUR / CENTRO / NORTE
        Escritura corS = new Escritura(32, "Córdoba Sur",
                Provincia.CORDOBA, Zona.SUR, 6000, 500, 1250,
                6500, 17000, 21000, 24000, 3000);
        Escritura corC = new Escritura(33, "Córdoba Centro",
                Provincia.CORDOBA, Zona.CENTRO, 6000, 450, 2400,
                6800, 16000, 19500, 23000, 3000);
        Escritura corN = new Escritura(34, "Córdoba Norte",
                Provincia.CORDOBA, Zona.NORTE, 6400, 550, 2850,
                8500, 19000, 23000, 27000, 3000);
        listEscrituras.add(corS); listEscrituras.add(corC); listEscrituras.add(corN);

        //Buenos Aires SUR / CENTRO / NORTE
        Escritura bsasS = new Escritura(37, "Buenos Aires Sur",
                Provincia.BUENOS_AIRES, Zona.SUR, 7000, 650, 3300,
                9500, 22000, 25000, 30000, 4000);
        Escritura bsasC = new Escritura(39, "Buenos Aires Centro",
                Provincia.BUENOS_AIRES, Zona.CENTRO, 7000, 650, 3300,
                9500, 22000, 25000, 30000, 4000);
        Escritura bsasN = new Escritura(40, "Buenos Aires Norte",
                Provincia.BUENOS_AIRES, Zona.NORTE, 7400, 1000, 4000,
                12000, 26000, 31000, 36000, 4000);
        listEscrituras.add(bsasS); listEscrituras.add(bsasC); listEscrituras.add(bsasN);

        return listEscrituras;
    }

    // Verifica si el Jugador dueño tiene TODAS las Escrituras (2 o 3) de una Provincia
    public Boolean ProvinciaCompleta(Escritura escritura){
        int cantEscrituras = 0;

        if (escritura.duenio == null){
            return false;
        }
        for(Escritura e : escritura.duenio.getEscrituras()){
            if (escritura.getProvincia().equals(e.getProvincia())){
                cantEscrituras++;
            }
        }

        if (cantEscrituras == 2 && (escritura.getProvincia().equals(Provincia.TUCUMAN) || escritura.getProvincia().equals(Provincia.RIO_NEGRO))){
            return true;
        } else if (cantEscrituras == 3){
            return true;
        } else {
            return false;
        }
    }

    // Funcionamiento del metodo:
    // fijarse cant. de Chacras y Estancias para determinar costo,
    // verificar si el Jugador dueño tiene todas las Escrituras de esa Provincia (si es asi el costo se duplica)
    // luego se cobra automaticamente (como indica el pdf) el alquiler al jugador (se le resta el dinero de su atributo cuenta),
    // y al Jugador dueño se le agrega a su cuenta el costo del alquiler,
    // por ultimo se devuelve un Integer (el costo del alquiler) para mostrar por consola.
    public Integer CobrarAlquiler(Jugador jugador){
        Integer costoAlquiler;

        if (this.cantEstancias == 1){
            costoAlquiler = alq1Estancia;
        } else {
            costoAlquiler = switch (cantChacras) {
                case 1 -> alq1Chacra;
                case 2 -> alq2Chacra;
                case 3 -> alq3Chacra;
                case 4 -> alq4Chacra;
                default -> alqCampoSolo;
            };
        }

        if (ProvinciaCompleta(this)){
            costoAlquiler *= 2;
        }

        if (this.duenio != jugador){ //asi no le cobra alquiler al dueño xd
            jugador.setCuenta(jugador.getCuenta()-costoAlquiler);
            this.duenio.setCuenta(this.duenio.getCuenta()+costoAlquiler);
        }

        return costoAlquiler;
    }

    //devuelve el valor total de si misma (la Escritura)
    // sirve para calcular el Patrimonio del Jugador
    public Integer ValorTotal(){
        int valorTotal = 0;

        //si está hipotecada, no le suma al patrimonio del dueño (creeria)
        if (!this.isEstadoHipoteca()){
            valorTotal = this.getValorEscritura();
            valorTotal += this.getCantChacras() * this.getCostoConstruccionChacra();
            valorTotal += this.getCantEstancias() * this.getCostoConstruccionEstancia();
        }

        return valorTotal;
    }
}
