package com.cytech.classeProjet;

import java.util.*;

/**
 * 
 */
public class ArbreGenealogique {

    private Double id;
    private NiveauVisibilite visibilite;
    private Personne racine;
    
    private List<Personne> personnes;
    
    public ArbreGenealogique(Double id, NiveauVisibilite visibilite, Personne racine) {
        this.id = id;
        this.visibilite = visibilite;
        this.racine = racine;
        this.personnes = new ArrayList<>();
        this.personnes.add(racine); // on commence par la racine
    }

    public void changerVisibiliteArbre(NiveauVisibilite nouvelleVisibilite) {
        this.visibilite = nouvelleVisibilite;
    }

   
    public Personne rechercherPersonne(String nom, String prenom) {
        for (Personne p : personnes) {
            if (p.getNom().equalsIgnoreCase(nom) && p.getPrenom().equalsIgnoreCase(prenom)) {
                return p;
            }
        }
        return null; // Si la personne n'est pas trouvée
    }

    /**
     * 
     */
    public void genererAffichageTextuel() {
        System.out.println("Arbre Généalogique (visibilité : " + visibilite + ")");
        for (Personne p : personnes) {
            p.afficherInfos();
        }
    }

    /**
     * 
     */
    public void genererAffichageGraphique() {
        System.out.println("[Graphique] - Non encore implémenté.");
    }

    public void ajouterPersonne(Personne p) {
        this.personnes.add(p);
    }

    public Double getId() {
        return id;
    }

    public NiveauVisibilite getVisibilite() {
        return visibilite;
    }

    public Personne getRacine() {
        return racine;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }
}
