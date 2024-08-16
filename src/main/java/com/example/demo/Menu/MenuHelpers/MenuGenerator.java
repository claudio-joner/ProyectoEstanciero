package com.example.demo.Menu.MenuHelpers;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuGenerator {
    private static String input_regex;
    private static String input;
    private static Scanner sc;

    public MenuGenerator() {
        this.sc = new Scanner(System.in);
    }

    public MenuGenerator(Scanner sc) {
        this.sc = sc;
    }

    /**
     *
     * @param menuTitle Para dar un titulo principal al menu que se vera arriba.
     * @param regex Para que se valide automaticamente el input del usuario.
     * @param options Las diferentes opciones que quieres en tu menu.
     * @return Retorna el input del usuario en forma de Integer.
     */
    public Integer generateMenu(String menuTitle, String regex, String options) {
        input_regex = regex;
        String menu = "\nMenu del " + menuTitle + "\n" +
                "=======================\n" +
                options +
                "=======================\n" +
                "Selecciona una opcion para comenzar el juego:";

        do {
            System.out.println(menu);
            input = sc.next().trim();
        } while (!matchRegex());

        return convertInput();
    }

    /**
     *
     *
     */

    public boolean generateMenuYesNo(String menuTitle, String question) {
        boolean flag = false;
        input_regex = "[yYnN]";
        String menu = "Menu de " + menuTitle + "\n" +
                "=======================\n" +
                question + "\n" +
                "=======================\n" +
                "Responde a la pregunta con Y (-> Yes) o N (-> No):";

        do {
            System.out.println(menu);
            input = sc.next().trim();
        } while (!matchRegex());

        if (input.equalsIgnoreCase("y")) {
            flag = true;
        }
        return flag;
    }

    /**
     *
     *
     */
    private boolean matchRegex() {
        Pattern pattern = Pattern.compile(input_regex);
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            System.out.println("Error en el formato del input.");
            return false;
        }
    }

    private Integer convertInput() {
        return Integer.parseInt(input);
    }
}
