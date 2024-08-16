package com.example.demo.Menu.MenuHelpers;

public class LetterByLetterPrinter {

    public static void println(String text) {
        try {
            for (int i = 0; i < text.length(); i++) {
                System.out.print(text.charAt(i));
                Thread.sleep(2); //
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
