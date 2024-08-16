package com.example.demo.Model;

import com.example.demo.Model.Carta;
import com.example.demo.Model.Enums.TipoCarta;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Stack;

@Data
public class Destino extends Tarjeta {

    //atributos
    private Stack<Carta> listaDestino; //No deberia ir aca

    //constructoreS
    public Destino()
    {
        listaDestino = new Stack<Carta>();
    }

    @Override
    public Stack<Carta> GenerarMazo() {

        // Avanzar / Retroceder / AvanzarCasillero / RetrocederCasillero / Cobrar / Pagar / Preso / SalirComisaria
        Carta carta1 = new Carta(TipoCarta.DESTINO,
            "5% de interés sobre cédulas hipotecarias. Cobre 500", 500,"Cobrar");
        Carta carta2 = new Carta(TipoCarta.DESTINO,
            "Con esta tarjeta sale usted de la Comisaría", 1,"SalirComisaria");
        Carta carta3 = new Carta(TipoCarta.DESTINO,
            "Marche preso directamente", 1,"Preso");
        Carta carta4 = new Carta(TipoCarta.DESTINO,
            "Devolución de impuesto. Cobre 400", 400,"Cobrar");

        Carta carta5 = new Carta(TipoCarta.DESTINO,
            "Pague su póliza de seguro con 1.000", 1000,"Pagar");
        Carta carta6 = new Carta(TipoCarta.DESTINO,
            "Ha ganado un concurso agrícola. Cobre 2.000", 2000,"Cobrar");
        Carta carta7 = new Carta(TipoCarta.DESTINO,
            "Error en los cálculos del Banco. Cobre 4.000", 4000,"Cobrar");
        Carta carta8 = new Carta(TipoCarta.DESTINO,
            "Gastos de farmacia. Pague 1.000", 1000,"Pagar");

        Carta carta9 = new Carta(TipoCarta.DESTINO,
            "Ha obtenido un segundo premio de belleza. Cobre 200", 200,"Cobrar");

        Carta carta10 = new Carta(TipoCarta.DESTINO,
            "Es su cumpleaños. Cobre 800 del Banco", 800,"Cobrar");

        Carta carta11 = new Carta(TipoCarta.DESTINO,
            "Ha ganado un concurso agrícola. Cobre 2.000", 2000,"Cobrar");
        Carta carta12 = new Carta(TipoCarta.DESTINO,
            "Hereda 2.000", 2000,"Cobrar");

        Carta carta13 = new Carta(TipoCarta.DESTINO,
            "Por venta de acciones. Cobre 1.000", 1000,"Cobrar");
        Carta carta14 = new Carta(TipoCarta.DESTINO,
            "Siga hasta la salida", 0,"AvanzarCasillero");
        Carta carta15 = new Carta(TipoCarta.DESTINO,
            "Vuelve atrás hasta Formosa Zona Sur", 1,"RetrocederCasillero");
        Carta carta16 = new Carta(TipoCarta.DESTINO,
            "Pague 200 de multa", 200,"Pagar");

        listaDestino.add(carta1);
        listaDestino.add(carta2);
        listaDestino.add(carta3);
        listaDestino.add(carta4);
        listaDestino.add(carta5);
        listaDestino.add(carta6);
        listaDestino.add(carta6);
        listaDestino.add(carta6);
        listaDestino.add(carta7);
        listaDestino.add(carta8);
        listaDestino.add(carta9);
        listaDestino.add(carta10);
        listaDestino.add(carta11);
        listaDestino.add(carta12);
        listaDestino.add(carta13);
        listaDestino.add(carta14);
        listaDestino.add(carta15);
        listaDestino.add(carta16);

        return listaDestino;
    }

}
