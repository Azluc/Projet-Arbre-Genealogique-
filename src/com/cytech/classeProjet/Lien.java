package com.cytech.classeProjet;

/**
 * Class representing a family relationship between two people in a genealogical tree.
 * This class stores information about the relationship between two people, including the type of relationship
 * and its specific name.
 */
public class Lien {
    /** The source person of the link (origin of the relationship) */
    private Personne personneSource;
    
    /** The destination person of the link (target of the relationship) */
    private Personne personneDestination;
    
    /** The type of relationship between the two people */
    private TypeRelation type;
    
    /** The specific name of the relationship (e.g., "father", "mother", "son", etc.) */
    private String nomRelation;
    
    /**
     * Returns a text representation of the link.
     * 
     * @return A string describing the link
     */
    @Override
    public String toString() {
        return "Lien [personneSource=" + personneSource + ", personneDestination=" + personneDestination + ", type="
                + type + ", nomRelation=" + nomRelation + ", getPersonneSource()=" + getPersonneSource()
                + ", getPersonneDestination()=" + getPersonneDestination() + ", getType()=" + getType()
                + ", getNomRelation()=" + getNomRelation() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }
    
    /**
     * Constructor for the Link class.
     * 
     * @param personneSource The source person of the link
     * @param personneDestination The destination person of the link
     * @param type The type of relationship
     * @param nomRelation The specific name of the relationship
     */
    public Lien(Personne personneSource, Personne personneDestination, TypeRelation type, String nomRelation) {
        super();
        this.personneSource = personneSource;
        this.personneDestination = personneDestination;
        this.type = type;
        this.nomRelation = nomRelation;
    }
    
    /**
     * Returns the source person of the link.
     * 
     * @return The source person
     */
    public Personne getPersonneSource() {
        return personneSource;
    }
    
    /**
     * Sets the source person of the link.
     * 
     * @param personneSource The new source person
     */
    public void setPersonneSource(Personne personneSource) {
        this.personneSource = personneSource;
    }
    
    /**
     * Returns the destination person of the link.
     * 
     * @return The destination person
     */
    public Personne getPersonneDestination() {
        return personneDestination;
    }
    
    /**
     * Sets the destination person of the link.
     * 
     * @param personneDestination The new destination person
     */
    public void setPersonneDestination(Personne personneDestination) {
        this.personneDestination = personneDestination;
    }
    
    /**
     * Returns the type of relationship of the link.
     * 
     * @return The type of relationship
     */
    public TypeRelation getType() {
        return type;
    }
    
    /**
     * Sets the type of relationship of the link.
     * 
     * @param type The new type of relationship
     */
    public void setType(TypeRelation type) {
        this.type = type;
    }
    
    /**
     * Returns the specific name of the relationship.
     * 
     * @return The name of the relationship
     */
    public String getNomRelation() {
        return nomRelation;
    }
    
    /**
     * Sets the specific name of the relationship.
     * 
     * @param nomRelation The new name of the relationship
     */
    public void setNomRelation(String nomRelation) {
        this.nomRelation = nomRelation;
    }
}