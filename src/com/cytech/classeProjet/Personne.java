package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * Class representing a person in a genealogical tree.
 * This class manages personal information and family relationships of a person.
 */
public class Personne {
    /** The person's last name */
    private String nom;
    
    /** The person's first name */
    private String prenom;
    
    /** The person's birth date */
    private Date dateNaissance;
    
    /** The person's death date (can be null) */
    private Date dateDeces;
    
    /** The person's gender */
    private Genre genre;
    
    /** The ID of the genealogical tree to which the person belongs */
    private int id_arbre;
    
    /** The person's depth in the genealogical tree */
    private int profondeur;
    
    /** The list of the person's parents */
    private List<Personne> parents;
    
    /** The list of the person's children */
    private List<Personne> enfants;
    
    /** The list of the person's siblings */
    private List<Personne> freresEtSoeurs;
    
    /** The person's spouse */
    private Personne conjoint;
    
    /** The person's family side (paternal, maternal, or neutral) */
    private Cote cote = Cote.NEUTRE;
    
    /** The person's nationality */
    private String nationalite;

    /**
     * Complete constructor for a person.
     * 
     * @param nom The last name
     * @param prenom The first name
     * @param dateNaissance The birth date
     * @param dateDeces The death date
     * @param genre The gender
     * @param id_arbre The tree ID
     * @param profondeur The depth in the tree
     */
    public Personne(String nom, String prenom, Date dateNaissance, Date dateDeces, Genre genre, int id_arbre, int profondeur) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.dateDeces = dateDeces;
        this.genre = genre;
        this.id_arbre = id_arbre;
        this.profondeur = profondeur;
        this.parents = new ArrayList<>();
        this.enfants = new ArrayList<>();
        this.freresEtSoeurs = new ArrayList<>();
    }
    
    /**
     * Simplified constructor for a person.
     * 
     * @param nom The last name
     * @param prenom The first name
     * @param nationalite The nationality
     * @param dateNaissance The birth date
     * @param dateDeces The death date
     */
    public Personne(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.dateNaissance = dateNaissance;
        this.dateDeces = dateDeces;
    }
    
    /**
     * Searches for a person in a set by their last name and first name.
     * 
     * @param personnes The set of people to search in
     * @param nom The last name to search for
     * @param prenom The first name to search for
     * @return The found person, or null if no person matches
     */
    public static Personne choisirPersonne(Set<Personne> personnes, String nom, String prenom) {
        if (personnes.isEmpty()) {
            System.out.print("empty");
        }
   
        for (Personne p : personnes) {
            String nomListe = p.getNom().trim();
            String prenomListe = p.getPrenom().trim();
            String nomRecherche = nom.trim();
            String prenomRecherche = prenom.trim();

            if (nomListe.equalsIgnoreCase(nomRecherche) &&
                prenomListe.equalsIgnoreCase(prenomRecherche)) {
                return p;
            }
        }
        return null;
    }
     
    /**
     * Adds a parent to the person.
     * Checks date consistency and updates family relationships.
     * 
     * @param parent The parent to add
     */
    public void ajouterParent(Personne parent) {
        if (!parents.contains(parent)) {
            parents.add(parent);
            if (!parent.getEnfants().contains(this)) {
                parent.getEnfants().add(this);
            }
        }
    }
    
    /**
     * Adds a child to the person.
     * Checks date consistency and updates family relationships.
     * 
     * @param enfant The child to add
     */
    public void ajouterEnfant(Personne enfant) {
        if (!enfants.contains(enfant)) {
            enfants.add(enfant);
            if (!enfant.getParents().contains(this)) {
                enfant.getParents().add(this);
            }
        }
    }
    
    /**
     * Adds a sibling to the person.
     * Updates family relationships.
     * 
     * @param frereOuSoeur The sibling to add
     * @param arbre The genealogical tree
     */
    public void ajouterFrereOuSoeur(Personne frereOuSoeur, ArbreGenealogique arbre) {
        if (!freresEtSoeurs.contains(frereOuSoeur)) {
            frereOuSoeur.getFreresEtSoeurs().add(this);
            freresEtSoeurs.add(frereOuSoeur);
            
            // Add common parents
            for (Personne parent : parents) {
                if (!frereOuSoeur.getParents().contains(parent)) {
                    frereOuSoeur.ajouterParent(parent);
                }
            }
        }
    }
    
    /**
     * Detects siblings among a collection of people.
     * 
     * @param personnes The collection of people to check
     * @param arbre The genealogical tree
     */
    public void detecterFreresEtSoeurs(Collection<Personne> personnes, ArbreGenealogique arbre) {
        for (Personne p1 : personnes) {
            for (Personne p2 : personnes) {
                if (p1 == p2) continue;
                for (Personne parent : p1.getParents()) {
                    if (p2.getParents().contains(parent)) {
                        p1.ajouterFrereOuSoeur(p2, arbre);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Checks if two people are identical.
     * 
     * @param autre The other person to compare with
     * @return true if the people are identical, false otherwise
     */
    public boolean estIdentique(Personne autre) {
        return this.nom.equalsIgnoreCase(autre.nom) && this.prenom.equalsIgnoreCase(autre.prenom);
    }
    
    /**
     * Returns the person's father.
     * 
     * @return The father, or null if not found
     */
    public Personne getPere() {
        for (Personne parent : parents) {
            if (parent.getGenre() == Genre.HOMME) return parent;
        }
        return null;
    }

    /**
     * Returns the person's mother.
     * 
     * @return The mother, or null if not found
     */
    public Personne getMere() {
        for (Personne parent : parents) {
            if (parent.getGenre() == Genre.FEMME) return parent;
        }
        return null;
    }

    /**
     * Returns the list of the person's uncles and aunts.
     * 
     * @return The list of uncles and aunts
     */
    public List<Personne> getOnclesEtTantes() {
        List<Personne> resultats = new ArrayList<>();
        for (Personne parent : parents) {
            resultats.addAll(parent.getFreresEtSoeurs());
        }
        return resultats;
    }
 
    /**
     * Checks if the person is a parent of another person.
     * 
     * @param enfant The person to check
     * @return true if the person is a parent, false otherwise
     */
    public boolean estParentDe(Personne enfant) {
        return enfant.getParents().contains(this);
    }
    
    /**
     * Checks equality between this person and another object.
     * 
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personne)) return false;
        Personne personne = (Personne) o;
        return nom.equalsIgnoreCase(personne.nom) && prenom.equalsIgnoreCase(personne.prenom);
    }

    /**
     * Returns the person's hash code.
     * 
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom.toLowerCase(), prenom.toLowerCase());
    }
    
    /**
     * Checks consistency between birth date and death date.
     * 
     * @param dateNaissance The birth date
     * @param dateDeces The death date
     * @return true if the dates are consistent, false otherwise
     */
    public static boolean verifierCoherenceDates(Date dateNaissance, Date dateDeces) {
        if (dateNaissance == null && dateDeces == null) {
            // Case where the user indicates neither birth date nor death date
            return false;
        }
        
        if (dateNaissance != null && dateDeces == null) {
            // Case where the user is still alive
            return true;
        }
        
        if (dateNaissance.before(dateDeces)) {
            // Case where the user indicates that birth date is before death date
            return true;
        } else {
            // If death date is before birth date
            return false;
        }
    }
   
    /**
     * Returns the person's full name.
     * 
     * @return The full name (first name + last name)
     */
    public String getNomComplet() {
        return nom + " " + prenom;
    }

    // Getters and setters with JavaDoc documentation
    
    /**
     * Returns the person's last name.
     * 
     * @return The last name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Returns the person's first name.
     * 
     * @return The first name
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Returns the person's birth date.
     * 
     * @return The birth date
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Sets the person's birth date.
     * 
     * @param dateNaissance The new birth date
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Returns the person's death date.
     * 
     * @return The death date, or null if the person is alive
     */
    public Date getDateDeces() {
        return dateDeces;
    }

    /**
     * Sets the person's death date.
     * 
     * @param dateDeces The new death date
     */
    public void setDateDeces(Date dateDeces) {
        this.dateDeces = dateDeces;
    }

    /**
     * Returns the person's gender.
     * 
     * @return The gender
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Sets the person's gender.
     * 
     * @param genre The new gender
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Returns the person's tree ID.
     * 
     * @return The tree ID
     */
    public int getId_arbre() {
        return id_arbre;
    }

    /**
     * Returns the person's depth in the tree.
     * 
     * @return The depth
     */
    public int getProfondeur() {
        return profondeur;
    }

    /**
     * Sets the person's depth in the tree.
     * 
     * @param profondeur The new depth
     */
    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

    /**
     * Returns the list of the person's parents.
     * 
     * @return The list of parents
     */
    public List<Personne> getParents() {
        return parents;
    }

    /**
     * Sets the list of the person's parents.
     * 
     * @param parents The new list of parents
     */
    public void setParents(List<Personne> parents) {
        this.parents = parents;
    }

    /**
     * Returns the list of the person's children.
     * 
     * @return The list of children
     */
    public List<Personne> getEnfants() {
        return enfants;
    }

    /**
     * Sets the list of the person's children.
     * 
     * @param enfants The new list of children
     */
    public void setEnfants(List<Personne> enfants) {
        this.enfants = enfants;
    }

    /**
     * Returns the list of the person's siblings.
     * 
     * @return The list of siblings
     */
    public List<Personne> getFreresEtSoeurs() {
        return freresEtSoeurs;
    }

    /**
     * Returns the person's family side.
     * 
     * @return The family side
     */
    public Cote getCote() {
        return cote;
    }

    /**
     * Sets the person's family side.
     * 
     * @param cote The new family side
     */
    public void setCote(Cote cote) {
        this.cote = cote;
    }

    /**
     * Returns the person's spouse.
     * 
     * @return The spouse, or null if the person is not married
     */
    public Personne getConjoint() {
        return conjoint;
    }

    /**
     * Sets the person's spouse.
     * 
     * @param conjoint The new spouse
     */
    public void setConjoint(Personne conjoint) {
        this.conjoint = conjoint;
    }

    /**
     * Returns the person's nationality.
     * 
     * @return The nationality
     */
    public String getNationalite() {
        return nationalite;
    }

    /**
     * Returns a text representation of the person.
     * 
     * @return A string describing the person
     */
    @Override
    public String toString() {
        return this.getPrenom() + " " + this.getNom();  
    }
    
    /**
     * Sets the person's nationality.
     * 
     * @param nationalite The new nationality
     */
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
}