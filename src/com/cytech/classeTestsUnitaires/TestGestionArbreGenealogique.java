package com.cytech.classeProjet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestGestionArbreGenealogique {

    private Map<Personne, Noeud> arbre;
    private List<LienParente> liens;

    private Personne parent;
    private Personne enfant;
    private Personne frere;
    private Personne oncle;
    private Personne cousin;

    @BeforeEach
    public void setup() {
        arbre = new HashMap<>();
        liens = new ArrayList<>();

        parent = new Personne("Durand", "Paul", "Française", new Date(), null);
        enfant = new Personne("Durand", "Emma", "Française", new Date(), null);
        frere = new Personne("Durand", "Lucas", "Française", new Date(), null);
        oncle = new Personne("Durand", "Marc", "Française", new Date(), null);
        cousin = new Personne("Durand", "Noé", "Française", new Date(), null);

        // Initialiser la racine (parent) dans l’arbre
        Noeud noeudParent = new Noeud();
        noeudParent.setPersonne(parent);
        arbre.put(parent, noeudParent);
    }

    @Test
    public void testAjouterFils() {
        GestionArbreGenealogique.ajouterFils(parent, enfant, arbre);

        assertTrue(arbre.containsKey(enfant));
        assertTrue(arbre.get(parent).getEnfants().contains(enfant));
        assertTrue(arbre.get(enfant).getParents().contains(parent));
    }

    @Test
    public void testAjouterFrereOuSoeur() {
        GestionArbreGenealogique.ajouterFils(parent, enfant, arbre);
        GestionArbreGenealogique.ajouterFrereOuSoeur(enfant, frere, arbre);

        assertTrue(arbre.get(frere).getParents().contains(parent));
        assertTrue(arbre.get(parent).getEnfants().contains(frere));
    }

    @Test
    public void testAjouterFrereParent() {
        // On crée les grands-parents
        Personne gp1 = new Personne("Martin", "Jean", "Française", new Date(), null);
        Personne gp2 = new Personne("Martin", "Sophie", "Française", new Date(), null);

        Noeud nGP1 = new Noeud();
        nGP1.setPersonne(gp1);
        arbre.put(gp1, nGP1);

        Noeud nGP2 = new Noeud();
        nGP2.setPersonne(gp2);
        arbre.put(gp2, nGP2);

        arbre.get(parent).ajouterParent(gp1);
        arbre.get(parent).ajouterParent(gp2);

        GestionArbreGenealogique.ajouterFrereParent(parent, oncle, arbre);

        assertTrue(arbre.containsKey(oncle));
        assertTrue(arbre.get(oncle).getParents().contains(gp1));
        assertTrue(arbre.get(oncle).getParents().contains(gp2));
    }

    @Test
    public void testAjouterCousinViaOncle() {
        GestionArbreGenealogique.ajouterFils(parent, enfant, arbre);
        GestionArbreGenealogique.ajouterFrereParent(parent, oncle, arbre);
        GestionArbreGenealogique.ajouterCousinViaOncle(oncle, cousin, arbre);

        assertTrue(arbre.containsKey(cousin));
        assertTrue(arbre.get(oncle).getEnfants().contains(cousin));
    }

    @Test
    public void testAjouterLienParente() {
        GestionArbreGenealogique.ajouterLienParente(enfant, cousin, TypeRelation.collateral, 2.0, liens);

        assertEquals(1, liens.size());
        LienParente lien = liens.get(0);
        assertEquals(enfant, lien.getSource());
        assertEquals(cousin, lien.getCible());
        assertEquals(TypeRelation.collateral, lien.getTypeRelation());
        assertEquals(2.0, lien.getProfondeur());
    }
}
