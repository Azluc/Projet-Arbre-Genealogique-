package com.cytech.classeProjet;

public class TestGenre {
    public static void main(String[] args) {
        Genre g = Genre.HOMME;

        if (g == Genre.HOMME) {
            System.out.println("Test Genre HOMME : OK");
        } else {
            System.out.println("Test Genre HOMME : ÉCHEC");
        }

        if (Genre.FEMME.toString().equals("FEMME")) {
            System.out.println("Test Genre FEMME : OK");
        } else {
            System.out.println("Test Genre FEMME : ÉCHEC");
        }
    }
}
