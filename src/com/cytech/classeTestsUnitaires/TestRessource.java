package com.cytech.classeProjet;

public class TestRessource_Console {
    public static void main(String[] args) {


        Ressource r = new Ressource(TypeDocument.PHOTO, "photo_de_famille.jpg");


        if (r.getTypeDocument() == TypeDocument.PHOTO) {
            System.out.println("Test 1 - TypeDocument : OK");
        } else {
            System.out.println("Test 1 - TypeDocument : ÉCHEC");
        }


        if (r.getContenu().equals("photo_de_famille.jpg")) {
            System.out.println("Test 2 - Contenu : OK");
        } else {
            System.out.println("Test 2 - Contenu : ÉCHEC");
        }

        if (r.getDatePartage() != null) {
            System.out.println("Test 3 - Date de partage : OK");
        } else {
            System.out.println("Test 3 - Date de partage : ÉCHEC");
        }

        try {
            r.partager();
            System.out.println("Test 4 - Méthode partager() : OK");
        } catch (Exception e) {
            System.out.println("Test 4 - Méthode partager() : ÉCHEC");
        }

        if (r.toString().contains("photo_de_famille.jpg")) {
            System.out.println("Test 5 - toString() : OK");
        } else {
            System.out.println("Test 5 - toString() : ÉCHEC");
        }
    }
}
