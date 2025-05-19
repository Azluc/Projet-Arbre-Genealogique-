package com.cytech.classeProjet;

import java.util.*;

public class TestGestionArbreGenealogique_Console {
    public static void main(String[] args) {

        Map<Personne, Noeud> arbre = new HashMap<>();
        List<LienParente> liens = new ArrayList<>();

        Personne parent = new Personne("Durand", "Paul", "Française", new Date(), null);
        Personne enfant = new Personne("Durand", "Emma", "Française", new Date(), null);

        Noeud noeudParent = new Noeud();
        noeudParent.setPersonne(parent);
        arbre.put(parent, noeudParent);

        GestionArbreGenealogique.ajouterFils(parent, enfant, arbre);

        // Test 1 : l’enfant a été ajouté dans la map
        if (arbre.containsKey(enfant)) {
            System.out.println("Test 1 - Enfant présent dans la map : OK");
        } else {
            System.out.println("Test 1 - Enfant présent dans la map : ÉCHEC");
        }

        // Test 2 : le parent connaît l’enfant
        if (arbre.get(parent).getEnfants().contains(enfant)) {
            System.out.println("Test 2 - Parent connaît enfant : OK");
        } else {
            System.out.println("Test 2 - Parent connaît enfant : ÉCHEC");
        }

        // Test 3 : l’enfant connaît son parent
        if (arbre.get(enfant).getParents().contains(parent)) {
            System.out.println("Test 3 - Enfant connaît parent : OK");
        } else {
            System.out.println("Test 3 - Enfant connaît parent : ÉCHEC");
        }

        // Test 4 : ajout d’un lien de parenté
        GestionArbreGenealogique.ajouterLienParente(parent, enfant, TypeRelation.ascendant, 1.0, liens);

        if (liens.size() == 1 && liens.get(0).getSource() == parent && liens.get(0).getCible() == enfant) {
            System.out.println("Test 4 - Lien de parenté ajouté : OK");
        } else {
            System.out.println("Test 4 - Lien de parenté ajouté : ÉCHEC");
        }
    }
}
