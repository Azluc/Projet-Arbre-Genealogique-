package com.cytech.classeProjet;

import java.util.Date;

public class TestLienParente_Console {
    public static void main(String[] args) {
        Personne source = new Personne("Dupont", "Alice", "Française", new Date(), null);
        Personne cible = new Personne("Dupont", "Louis", "Française", new Date(), null);

        LienParente lien = new LienParente(source, cible, TypeRelation.collateral, 2.0);

        // Test 1 : Source correcte
        if (lien.getSource() == source) {
            System.out.println("Test 1 - Source correcte : OK");
        } else {
            System.out.println("Test 1 - Source correcte : ÉCHEC");
        }

        // Test 2 : Cible correcte
        if (lien.getCible() == cible) {
            System.out.println("Test 2 - Cible correcte : OK");
        } else {
            System.out.println("Test 2 - Cible correcte : ÉCHEC");
        }

        // Test 3 : Type relation
        if (lien.getTypeRelation() == TypeRelation.collateral) {
            System.out.println("Test 3 - Type relation correcte : OK");
        } else {
            System.out.println("Test 3 - Type relation correcte : ÉCHEC");
        }

        // Test 4 : Profondeur
        if (lien.getProfondeur() == 2.0) {
            System.out.println("Test 4 - Profondeur correcte : OK");
        } else {
            System.out.println("Test 4 - Profondeur correcte : ÉCHEC");
        }

        // Test 5 : toString() non vide
        if (lien.toString() != null && !lien.toString().isEmpty()) {
            System.out.println("Test 5 - toString() fonctionne : OK");
        } else {
            System.out.println("Test 5 - toString() fonctionne : ÉCHEC");
        }
    }
}
