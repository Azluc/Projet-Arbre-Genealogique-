package com.cytech.classeProjet;

public class TestNiveauVisibilite_Console {
    public static void main(String[] args) {

        // Test 1 : toString
        if (NiveauVisibilite.public_.toString().equals("Public")) {
            System.out.println("Test 1 - toString Public : OK");
        } else {
            System.out.println("Test 1 - toString Public : ÉCHEC");
        }

        // Test 2 : getLabel
        if (NiveauVisibilite.prive.getLabel().equals("Privé")) {
            System.out.println("Test 2 - getLabel Privé : OK");
        } else {
            System.out.println("Test 2 - getLabel Privé : ÉCHEC");
        }

        // Test 3 : fromString avec texte
        try {
            NiveauVisibilite nv = NiveauVisibilite.fromString("protégé");
            if (nv == NiveauVisibilite.protege) {
                System.out.println("Test 3 - fromString 'protégé' : OK");
            } else {
                System.out.println("Test 3 - fromString 'protégé' : ÉCHEC");
            }
        } catch (Exception e) {
            System.out.println("Test 3 - fromString 'protégé' : ÉCHEC (Exception)");
        }

        // Test 4 : fromString avec chiffre
        try {
            NiveauVisibilite nv = NiveauVisibilite.fromString("1");
            if (nv == NiveauVisibilite.public_) {
                System.out.println("Test 4 - fromString '1' : OK");
            } else {
                System.out.println("Test 4 - fromString '1' : ÉCHEC");
            }
        } catch (Exception e) {
            System.out.println("Test 4 - fromString '1' : ÉCHEC (Exception)");
        }

        // Test 5 : fromString invalide
        try {
            NiveauVisibilite.fromString("invalide");
            System.out.println("Test 5 - fromString 'invalide' : ÉCHEC (aucune exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 - fromString 'invalide' : OK (exception capturée)");
        }
    }
}
