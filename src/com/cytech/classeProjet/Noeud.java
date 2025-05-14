package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.List;

public class Noeud {

    private List<Personne> parent;
    private List<Personne> enfant;

    public Noeud() {
        this.parent = new ArrayList<>();
        this.enfant = new ArrayList<>();
    }

    public void ajouterPersonne(Personne personne) {
        if (personne != null) {
            enfant.add(personne);
            System.out.println(" l'enfant ajouté est : " + personne.getPrenom() + " " + personne.getNom());
        }
    }

    public void modifierPersonne(Personne nouvellePersonne) {
        boolean modifie = false;

        for (int i = 0; i < parent.size(); i++) {
            Personne p = parent.get(i);
            if (p.getNom().equals(nouvellePersonne.getNom()) && p.getPrenom().equals(nouvellePersonne.getPrenom())) {
                parent.set(i, nouvellePersonne);
                modifie = true;
                System.out.println(" Le parent est modifié : " + nouvellePersonne.getPrenom() + " " + nouvellePersonne.getNom());
            }
        }

        for (int i = 0; i < enfant.size(); i++) {
            Personne p = enfant.get(i);
            if (p.getNom().equals(nouvellePersonne.getNom()) && p.getPrenom().equals(nouvellePersonne.getPrenom())) {
                enfant.set(i, nouvellePersonne);
                modifie = true;
                System.out.println(" L'enfant est modifé : " + nouvellePersonne.getPrenom() + " " + nouvellePersonne.getNom());
            }
        }

        if (!modifie) {
            System.out.println(" La personne est non trouvée dans ce nœud.");
        }
    }

    public List<Personne> getParents() {
        return parent;
    }

    public List<Personne> getEnfants() {
        return enfant;
    }
}
