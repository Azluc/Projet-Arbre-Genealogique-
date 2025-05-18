package com.cytech.classeProjet;

import java.util.*;

/**
 * Classe représentant un arbre généalogique contenant des personnes
 * et une visibilité associée.
 */
public class ArbreGenealogique {

    private Double id;
    private NiveauVisibilite visibilite;
    private Personne racine;
    private List<Personne> personnes;

    /**
     * Constructeur de l'arbre généalogique.
     *
     * @param id         identifiant unique de l’arbre
     * @param visibilite niveau de visibilité
     * @param racine     personne racine (point de départ)
     */
    public ArbreGenealogique(Double id, NiveauVisibilite visibilite, Personne racine) {
        this.id = id;
        this.visibilite = visibilite;
        this.racine = racine;
        this.personnes = new ArrayList<>();
        this.personnes.add(racine); // Ajout de la racine au départ
    }

    /**
     * Change le niveau de visibilité de l'arbre.
     *
     * @param nouvelleVisibilite le nouveau niveau
     */
    public void changerVisibiliteArbre(NiveauVisibilite nouvelleVisibilite) {
        this.visibilite = nouvelleVisibilite;
    }

    /**
     * Ajoute une personne à l’arbre.
     *
     * @param p la personne à ajouter
     */
    public void ajouterPersonne(Personne p) {
        this.personnes.add(p);
    }

    /**
     * Recherche une personne par nom et prénom.
     *
     * @param nom    nom à rechercher
     * @param prenom prénom à rechercher
     * @return la personne trouvée, sinon null
     */
    public Personne rechercherPersonne(String nom, String prenom) {
        for (Personne p : personnes) {
            if (p.getNom().equalsIgnoreCase(nom) && p.getPrenom().equalsIgnoreCase(prenom)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Affiche l’arbre de façon textuelle dans la console.
     */
    public void genererAffichageTextuel() {
        System.out.println("Arbre Généalogique (visibilité : " + visibilite + ")");
        for (Personne p : personnes) {
            p.afficherInfos();
        }
    }

    /**
     * Affichage graphique (non implémenté pour le moment).
     */
    public void genererAffichageGraphique() {
        System.out.println("[Affichage graphique non implémenté]");
    }

    // --- Getters ---

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
