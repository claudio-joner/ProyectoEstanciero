package com.example.demo.Model;

import com.example.demo.Model.Carta;
import com.example.demo.Model.Enums.TipoCarta;
import lombok.Data;


import java.util.Stack;

@Data
public class Suerte extends Tarjeta {

    private Stack<Carta> listaSuerte;

    public Suerte() {
        listaSuerte = new Stack<Carta>();
    }

    @Override
    public Stack<Carta> GenerarMazo() {

        // Avanzar / Retroceder / AvanzarCasillero / RetrocederCasillero / Cobrar / Pagar / Preso / SalirComisaria
        Carta carta1 = new Carta(TipoCarta.SUERTE,"Marche preso directamente.",
                1,"Preso");
        Carta carta2 = new Carta(TipoCarta.SUERTE,"Ha ganado la grande. Cobre 10.000",
                10000,"Cobrar");
        Carta carta3 = new Carta(TipoCarta.SUERTE,"Haga un paseo hasta la Bodega. Si pasa por la salida cobre 5.000",
                16,"AvanzarCasillero");
        Carta carta4 = new Carta(TipoCarta.SUERTE,"Siga hasta Buenos Aires, Zona Norte",
                40,"AvanzarCasillero");

        Carta carta5 = new Carta(TipoCarta.SUERTE,"Siga hasta Salta, Zona Norte. Si pasa por la salida cobre 5.000",
                13,"AvanzarCasillero");
        Carta carta6 = new Carta(TipoCarta.SUERTE,"Multa por exceso de velocidad. Pague 300",
                300,"Pagar");
        Carta carta7 = new Carta(TipoCarta.SUERTE,"Ganó en las carreras. Cobre 3.000",
                3000,"Cobrar");
        Carta carta8 = new Carta(TipoCarta.SUERTE, "Cobre 1.000 por intereses bancarios",
                1000,"Cobrar");

        Carta carta9 = new Carta(TipoCarta.SUERTE,"Siga hasta la salida.",
                0,"AvanzarCasillero");

        Carta carta10 = new Carta(TipoCarta.SUERTE,"Vuelva tres pasos atrás",
                3,"Retroceder");
        Carta carta11 = new Carta(TipoCarta.SUERTE, "Multa caminera. Pague 400",
                400,"Pagar");

        //ver metodo PagarEnElMomento en Clase Tarjeta para ver implementacion
        Carta carta12 = new Carta(TipoCarta.SUERTE, "Por compra de semilla pague al Banco 800 por cada chacra. 3.000 por cada estancia",
                800,"PagarEnElMomento");

        Carta carta13 = new Carta(TipoCarta.SUERTE,"Siga hasta Santa Fé, Zona Norte. Si pasa por la salida cobre 5.000",
                26,"AvanzarCasillero");

        //ver metodo PagarEnElMomento en Clase Tarjeta para ver implementacion
        Carta carta14 = new Carta(TipoCarta.SUERTE,"Sus propiedades tienen que ser reparadas. Pague 500 por c/ chacra y 3.000 por c/ estancia",
                500,"PagarEnElMomento");

        Carta carta15 = new Carta(TipoCarta.SUERTE,"Habeas Corpus concedido. Con esta tarjeta sale usted gratuitamente de la Comisaría",
                1,"SalirComisaria");
        Carta carta16 = new Carta(TipoCarta.SUERTE,"Pague 3.000 por gastos colegiales",
                3000,"Pagar");

        listaSuerte.add(carta1);
        listaSuerte.add(carta2);
        listaSuerte.add(carta3);
        listaSuerte.add(carta4);
        listaSuerte.add(carta5);
        listaSuerte.add(carta6);
        listaSuerte.add(carta6);
        listaSuerte.add(carta6);
        listaSuerte.add(carta7);
        listaSuerte.add(carta8);
        listaSuerte.add(carta9);
        listaSuerte.add(carta10);
        listaSuerte.add(carta11);
        listaSuerte.add(carta12);
        listaSuerte.add(carta13);
        listaSuerte.add(carta14);
        listaSuerte.add(carta15);
        listaSuerte.add(carta16);

        return listaSuerte;
    }

}
