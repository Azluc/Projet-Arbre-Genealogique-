package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing a genealogical tree.
 * This class manages the structure and relationships between people in the genealogical tree.
 * It contains the root of the tree, its identifier, the set of people and their family relationships.
 */
public class ArbreGenealogique {
    /** The root person of the genealogical tree */
    private Personne racine;
    
    /** The unique identifier of the genealogical tree */
    private int id_arbre;
    
    /** The set of people in the genealogical tree */
    private Set<Personne> personnes;
    
    /** The family relationships between people in the tree */
    private LienParente liensParente;
    
    /**
     * Default constructor for the genealogical tree.
     * Initializes a new LienParente object.
     */
    public ArbreGenealogique() {
        this.liensParente = new LienParente(this);
    }
    
    /**
     * Returns the family relationships of the tree.
     * 
     * @return The LienParente object containing the family relationships
     */
    public LienParente getLiensParente() {
        return liensParente;
    }

    /**
     * Sets the family relationships of the tree.
     * 
     * @param liensParente The new LienParente object
     */
    public void setLiensParente(LienParente liensParente) {
        this.liensParente = liensParente;
    }
 
    /**
     * Complete constructor for the genealogical tree.
     * 
     * @param racine The root person of the tree
     * @param id_arbre The tree identifier
     * @param personnes The set of people in the tree
     */
    public ArbreGenealogique(Personne racine, int id_arbre, Set<Personne> personnes) {
        super();
        this.racine = racine;
        this.id_arbre = id_arbre;
        this.personnes = personnes;
        this.racine.setProfondeur(0);
        this.liensParente = new LienParente(this);
    }

    /**
     * Returns a text representation of the genealogical tree.
     * 
     * @return A string describing the tree
     */
    @Override
    public String toString() {
        return "ArbreGenealogique [racine=" + racine + ", id_arbre=" + id_arbre + ", personnes=" + personnes
                + ", liensParente=" + liensParente + ", getLiensParente()=" + getLiensParente() + ", getRacine()="
                + getRacine() + ", getId_arbre()=" + getId_arbre() + ", getPersonnes()=" + getPersonnes()
                + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
                + "]";
    }

    /**
     * Returns the root person of the tree.
     * 
     * @return The root person
     */
    public Personne getRacine() {
        return racine;
    }

    /**
     * Returns the tree identifier.
     * 
     * @return The tree identifier
     */
    public int getId_arbre() {
        return id_arbre;
    }

    /**
     * Returns the set of people in the tree.
     * 
     * @return The set of people
     */
    public Set<Personne> getPersonnes() {
        return personnes;
    }
    
    /**
     * Removes a person from the tree if they are a leaf (has no children or parents).
     * 
     * @param p The person to remove
     */
    public void supprimerSiFeuille(Personne p) {
        if (p.getEnfants().isEmpty() || p.getParents().isEmpty()) {
            // Remove links with parents
            for (Personne parent : p.getParents()) {
                parent.getEnfants().remove(p);
            }

            // Remove link with spouse
            if (p.getConjoint() != null) {
                p.getConjoint().setConjoint(null);
            }

            // Remove from tree
            personnes.remove(p);  
            System.out.println(p.getNomComplet() + " has been removed from the tree.");
        } else {
            System.out.println(p.getNomComplet() + " cannot be removed: not a leaf.");
        }
    }
    
    /**
     * Finds a person in the tree by their name.
     * 
     * @param nom The last name to search for
     * @param prenom The first name to search for
     * @return The found person, or null if no person matches
     */
    public Personne trouverPersonneParNomPrenom(String nom, String prenom) {
        for (Personne p : personnes) {
            if (p.getNom().equalsIgnoreCase(nom) && p.getPrenom().equalsIgnoreCase(prenom)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Displays all relationships in the tree from the root.
     * This method also displays couples and removes people without direct links to the root.
     * 
     * @param arbre The genealogical tree to analyze
     */
    public static void afficherRelations(ArbreGenealogique arbre) {
        System.out.println("\nRelationships in the tree from the root:");
        Set<String> unionsAffichees = new HashSet<>();
        List<Personne> personnesASupprimer = new ArrayList<>();
        // Implementation details...
    }

    /**
     * Définit la personne racine de l'arbre.
     * 
     * @param racine La nouvelle personne racine
     */
    public void setRacine(Personne racine) {
        this.racine = racine;
    }

    /**
     * Définit l'identifiant de l'arbre.
     * 
     * @param id_arbre Le nouvel identifiant
     */
    public void setId_arbre(int id_arbre) {
        this.id_arbre = id_arbre;
    }

    /**
     * Définit l'ensemble des personnes dans l'arbre.
     * 
     * @param personnes Le nouvel ensemble de personnes
     */
    public void setPersonnes(Set<Personne> personnes) {
        this.personnes = personnes;
    }
}
