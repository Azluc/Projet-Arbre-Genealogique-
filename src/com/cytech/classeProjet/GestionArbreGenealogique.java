package com.cytech.classeProjet;

import java.util.*;

public class GestionArbreGenealogique {

    public static void ajouterFils(Personne parent, Personne enfant, Map<Personne, Noeud> arbre) {
        Noeud noeudParent = arbre.get(parent);
        Noeud noeudEnfant = new Noeud();
        noeudEnfant.setPersonne(enfant);

        noeudParent.ajouterEnfant(enfant);
        noeudEnfant.ajouterParent(parent);

        arbre.put(enfant, noeudEnfant);
    }

    public static void ajouterFrereOuSoeur(Personne personne, Personne frere, Map<Personne, Noeud> arbre) {
        Noeud noeudPerso = arbre.get(personne);
        Noeud noeudFrere = new Noeud();
        noeudFrere.setPersonne(frere);

        for (Personne parent : noeudPerso.getParents()) {
            Noeud noeudParent = arbre.get(parent);
            noeudParent.ajouterEnfant(frere);
            noeudFrere.ajouterParent(parent);
        }

        arbre.put(frere, noeudFrere);
    }

    public static void ajouterFrereParent(Personne parent, Personne oncle, Map<Personne, Noeud> arbre) {
        Noeud noeudParent = arbre.get(parent);
        Noeud noeudOncle = new Noeud();
        noeudOncle.setPersonne(oncle);

        for (Personne grandParent : noeudParent.getParents()) {
            Noeud noeudGP = arbre.get(grandParent);
            noeudGP.ajouterEnfant(oncle);
            noeudOncle.ajouterParent(grandParent);
        }

        arbre.put(oncle, noeudOncle);
    }

    public static void ajouterCousinViaOncle(Personne oncle, Personne cousin, Map<Personne, Noeud> arbre) {
        ajouterFils(oncle, cousin, arbre);
    }

    public static void ajouterCousinDirect(Personne moi, Personne cousin, Map<Personne, Noeud> arbre, List<LienParente> liens) {
        Noeud noeudCousin = new Noeud();
        noeudCousin.setPersonne(cousin);
        arbre.put(cousin, noeudCousin);

        LienParente lien = new Lien
