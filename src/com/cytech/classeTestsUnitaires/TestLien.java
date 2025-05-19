package com.cytech.classeProjet;

import java.util.Date;

public class TestLien {
    public static void main(String[] args) {
        Personne source = new Personne("Martin", "Alice", "Française", new Date(), null);
        Personne cible = new Personne("Martin", "Louis", "Française", new Date(), null);

        Lien lien = new Lien(source, cible, TypeRelation.collateral, "Cousin");

        if (lien.getPersonneSource() == source) {
            System.out.println("Test source : OK");
        } else {
            System.out.println("Test source : ÉCHEC");
        }

        if (lien.getPersonneDestination() == cible) {
            System.out.println("Test cible : OK");
        } else {
            System.out.println("Test cible : ÉCHEC");
        }

        if ("Cousin".equals(lien.getNomRelation())) {
            System.out.println("Test nom relation : OK");
        } else {
            System.out.println("Test nom relation : ÉCHEC");
        }
    }
}
