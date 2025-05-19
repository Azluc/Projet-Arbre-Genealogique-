package com.cytech.classeProjet;

import java.util.Date;

public class TestNoeud_Console {
    public static void main(String[] args) {

        // Création d’un nœud
        Noeud noeud = new Noeud();

        Personne parent = new Personne("Durand", "Paul", "Française", new Date(), null);
        Personne enfant = new Personne("Durand", "Emma", "Française", new Date(), null);
        Personne centrale = new Personne("Durand", "Lucie", "Française", new Date(), null);

        // Test 1 : setPersonne / getPersonne
        noeud.setPersonne(centrale);
        if (noeud.getPersonne() == centrale) {
            System.out.println("Test 1 - set/getPersonne : OK");
        } else {
            System.out.println("Test 1 - set/getPersonne : ÉCHEC");
        }

        // Test 2 : ajouterParent()
        noeud.ajouterParent(parent);
        if (noeud.getParents().contains(parent)) {
            System.out.println("Test 2 - ajouterParent : OK");
        } else {
            System.out.println("Test 2 - ajouterParent : ÉCHEC");
        }

        // Test 3 : ajouterEnfant()
        noeud.ajouterEnfant(enfant);
        if (noeud.getEnfants().contains(enfant)) {
            System.out.println("Test 3 - ajouterEnfant : OK");
        } else {
            System.out.println("Test 3 - ajouterEnfant : ÉCHEC");
        }

        // Test 4 : ajout double = pas de doublon
        noeud.ajouterParent(parent);
        int nbParents = noeud.getParents().size();
        if (nbParents == 1) {
            System.out.println("Test 4 - Pas de doublon parent : OK");
        } else {
            System.out.println("Test 4 - Pas de doublon parent : ÉCHEC");
        }
    }
}
