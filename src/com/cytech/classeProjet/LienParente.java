package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.List;

import com.cytech.gestionBDD.GestionLienParenteBDD;
import com.cytech.gestionBDD.GestionPersonneBDD;

/**
 * Class managing family relationships in a genealogical tree.
 * This class maintains a list of links between people and handles their persistence in the database.
 */
public class LienParente {
    /** Reference to the genealogical tree to which the links belong */
    private ArbreGenealogique arbre;
    
    /** List of individual family relationship links */
    private List<Lien> liens;

    /**
     * Constructor for the FamilyRelationship class.
     * 
     * @param arbre The genealogical tree to which the links belong
     */
    public LienParente(ArbreGenealogique arbre) {
        this.arbre = arbre;
        this.liens = new ArrayList<>();
    }

    /**
     * Returns a text representation of the family relationships.
     * 
     * @return A string describing the family relationships
     */
    @Override
    public String toString() {
        return "LienParente [arbre=" + arbre + ", liens=" + liens + ", getLiens()=" + getLiens() + ", getArbre()="
                + getArbre() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

    /**
     * Adds a family relationship to the genealogical tree while avoiding duplicates.
     * A link is considered a duplicate if it has the same source, destination, and relationship type.
     * If a duplicate is found, the existing link is updated with the new relationship name if necessary.
     * 
     * @param p1 The first person in the relationship
     * @param p2 The second person in the relationship
     * @param typeRelation The type of relationship between the people
     * @param nomRelation The name of the relationship
     */
    public void ajouterLien(Personne p1, Personne p2, TypeRelation typeRelation, String nomRelation) {
        // Check if a similar link already exists
        for (Lien lien : liens) {
            if (lienSimilaire(lien, p1, p2, typeRelation)) {
                // If a similar link exists but the relationship name is different, update it
                if (!lien.getNomRelation().equals(nomRelation)) {
                    lien.setNomRelation(nomRelation);
                }
                return; // Exit because a similar link was found and handled
            }
        }
        
        // If we get here, no similar link exists, so we add it
        liens.add(new Lien(p1, p2, typeRelation, nomRelation));
        
        GestionLienParenteBDD.enregistrerLienDansBDD(p1, p2, typeRelation, nomRelation);
        
        GestionPersonneBDD.ajouterPersonne(p1, p1.getId_arbre());
        GestionPersonneBDD.ajouterPersonne(p2, p1.getId_arbre());
    }
    
    /**
     * Checks if a link is similar (same source, destination, and type) to the specified link.
     * 
     * @param lien The link to compare
     * @param source The source person
     * @param destination The destination person
     * @param type The type of relationship
     * @return true if the links are similar, false otherwise
     */
    private boolean lienSimilaire(Lien lien, Personne source, Personne destination, TypeRelation type) {
        return lien.getPersonneSource().equals(source) &&
               lien.getPersonneDestination().equals(destination) &&
               lien.getType() == type;
    }
    
    /**
     * Cleans up the links by removing duplicates.
     * This method can be called to clean up the existing list of links.
     */
    public void nettoyerDoublons() {
        List<Lien> liensUniques = new ArrayList<>();
        
        for (Lien lien : liens) {
            boolean estUnique = true;
            
            // Check if this link already exists in our list of unique links
            for (Lien lienUnique : liensUniques) {
                if (lienSimilaire(lien, lienUnique.getPersonneSource(), 
                                  lienUnique.getPersonneDestination(), lienUnique.getType())) {
                    estUnique = false;
                    break;
                }
            }
            
            if (estUnique) {
                liensUniques.add(lien);
            }
        }
        
        // Replace the old list with the list without duplicates
        this.liens = liensUniques;
    }

    /**
     * Returns the list of family relationship links.
     * 
     * @return The list of links
     */
    public List<Lien> getLiens() {
        return liens;
    }

    /**
     * Returns the genealogical tree associated with the links.
     * 
     * @return The genealogical tree
     */
    public ArbreGenealogique getArbre() {
        return arbre;
    }

    /**
     * Sets the genealogical tree associated with the links.
     * 
     * @param arbre The new genealogical tree
     */
    public void setArbre(ArbreGenealogique arbre) {
        this.arbre = arbre;
    }

    /**
     * Sets the list of family relationship links.
     * 
     * @param liens The new list of links
     */
    public void setLiensParente(List<Lien> liens) {
        this.liens = liens;
    }
}
