package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un nœud dans l'arbre généalogique
 * avec une personne, ses parents et ses enfants.
 */
public class Noeud {

    private Personne personne; // personne centrale de ce nœud
    private List<Personne> parents;
    private List<Personne> enfants;

    public Noeud() {
        this.parents = new ArrayList<>();
        this.enfants = new ArrayList<>();
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void ajouterParent(Personne parent) {
        if (parent != null && !parents.contains(parent)) {
            parents.add(parent);
            System.out.println("Parent ajouté : " + parent.getPrenom() + " " + parent.getNom());
        }
    }

    public void ajouterEnfant(Personne enfant) {
        if (enfant != null && !enfants.contains(enfant)) {
            enfants.add(enfant);
            System.out.println("Enfant ajouté : " + enfant.getPrenom() + " " + enfant.getNom());
        }
    }

    public void modifierPersonne(Personne nouvellePersonne) {
        if (personne != null && personne.getNom().equals(nouvellePersonne.getNom())
                && personne.getPrenom().equals(nouvellePersonne.getPrenom())) {
            this.personne = nouvellePersonne;
            System.out.println("Personne du nœud modifiée : " + nouvellePersonne.getPrenom() + " " + nouvellePersonne.getNom());
        } else {
            System.out.println("Aucune correspondance dans ce nœud pour modifier.");
        }
    }

    public List<Personne> getParents() {
        return parents;
    }

    public List<Personne> getEnfants() {
        return enfants;
    }
}
