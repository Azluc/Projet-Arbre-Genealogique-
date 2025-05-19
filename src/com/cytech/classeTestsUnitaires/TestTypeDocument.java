package com.cytech.classeProjet;

public class TestTypeDocument {
    public static void main(String[] args) {

        // Test 1 : valeur PHOTO
        if (TypeDocument.PHOTO.toString().equals("PHOTO")) {
            System.out.println("Test 1 - PHOTO : OK");
        } else {
            System.out.println("Test 1 - PHOTO : ÉCHEC");
        }

        // Test 2 : valeur VIDEO
        if (TypeDocument.VIDEO.name().equals("VIDEO")) {
            System.out.println("Test 2 - VIDEO : OK");
        } else {
            System.out.println("Test 2 - VIDEO : ÉCHEC");
        }

        // Test 3 : enum par valueOf
        try {
            TypeDocument doc = TypeDocument.valueOf("MESSAGE");
            if (doc == TypeDocument.MESSAGE) {
                System.out.println("Test 3 - MESSAGE via valueOf : OK");
            } else {
                System.out.println("Test 3 - MESSAGE via valueOf : ÉCHEC");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Test 3 - MESSAGE via valueOf : ÉCHEC - Exception");
        }

        // Test 4 : valeur inexistante
        try {
            TypeDocument.valueOf("TEXTE");
            System.out.println("Test 4 - Invalide : ÉCHEC (pas d'erreur attendue)");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 4 - Invalide : OK (erreur capturée)");
        }
    }
}
