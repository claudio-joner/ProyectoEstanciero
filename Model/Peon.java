package Model;

import java.util.Scanner;

public class Peon {

    private String color = "" ;

    public Peon() {
        this.color = "";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void seleccionarColor(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Seleccione el color del peon: 1-Azul 2-Verde 3-Rojo 4-Amarillo");
        int opcColor = scan.nextInt();

        while (true){
            if( opcColor == 1 ){
                setColor("Azul");
                break;
            } else if (opcColor == 2) {
                setColor("Verde");
                break;
            } else if (opcColor == 3) {
                setColor("Rojo");
                break;
            } else if (opcColor == 4) {
                setColor("Amarillo");
                break;
            }
            else{
                System.out.println("Opcion incorrecta.Seleccione un valor");
                opcColor = scan.nextInt();
            }
        }
        scan.close();
    }
}
