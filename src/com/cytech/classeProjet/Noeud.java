package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.List;


public class Noeud {

    private Personne personne;
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
        }
    }

    public void ajouterEnfant(Personne enfant) {
        if (enfant != null && !enfants.contains(enfant)) {
            enfants.add(enfant);
        }
    }

    public List<Personne> getParents() {
        return parents;
    }

    public List<Personne> getEnfants() {
        return enfants;
    }
}
