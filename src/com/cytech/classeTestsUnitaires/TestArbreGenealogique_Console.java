package com.cytech.classeProjet;

import java.util.*;

public class TestArbreGenealogique_Console {

    public static void main(String[] args) {
        Personne racine = new Personne("Morel", "Léa", "Française", new Date(), null);
        ArbreGenealogique arbre = new ArbreGenealogique(1.0, NiveauVisibilite.public_, racine);

        // Test 1 : Vérification de la racine
        if (arbre.getRacine() == racine) {
            System.out.println("Test 1 - Racine correcte : OK");
        } else {
            System.out.println("Test 1 - Racine correcte : ÉCHEC");
        }

        // Test 2 : Ajout d’une personne
        Personne fils = new Personne("Morel", "Julien", "Française", new Date(), null);
        arbre.ajouterPersonne(fils);

        if (arbre.getPersonnes().contains(fils)) {
            System.out.println("Test 2 - Ajout personne : OK");
        } else {
            System.out.println("Test 2 - Ajout personne : ÉCHEC");
        }

        // Test 3 : Recherche d'une personne existante
        Personne recherche = arbre.rechercherPersonne("Morel", "Julien");

        if (recherche != null && recherche == fils) {
            System.out.println("Test 3 - Recherche personne : OK");
        } else {
            System.out.println("Test 3 - Recherche personne : ÉCHEC");
        }

        // Test 4 : Changement de visibilité
        arbre.changerVisibiliteArbre(NiveauVisibilite.protege);

        if (arbre.getVisibilite() == NiveauVisibilite.protege) {
            System.out.println("Test 4 - Changement visibilité : OK");
        } else {
            System.out.println("Test 4 - Changement visibilité : ÉCHEC");
        }

        // Test 5 : Affichage textuel (pas de crash attendu)
        try {
            arbre.genererAffichageTextuel();
            System.out.println("Test 5 - Affichage textuel : OK");
        } catch (Exception e) {
            System.out.println("Test 5 - Affichage textuel : ÉCHEC");
        }
    }
}
