package com.cytech.classeProjet;

/**
 * Enumeration defining the possible types of relationships between two people in a genealogical tree.
 * Each relationship type is associated with a label and an orientation for display purposes.
 */
public enum TypeRelation {
    /** Sibling relationship */
    FRERE_SOEUR("Brother/Sister", Orientation.HORIZONTAL),
    
    /** Parent-child relationship */
    PARENT_ENFANT("Parent-Child", Orientation.VERTICAL),
    
    /** Union relationship (marriage, etc.) */
    UNION("Union", Orientation.HORIZONTAL);

    /** The label of the relationship */
    private final String label;
    
    /** The orientation of the relationship for display */
    private final Orientation orientation;

    /**
     * Constructor for the TypeRelation enumeration.
     * 
     * @param label The label of the relationship
     * @param orientation The orientation of the relationship
     */
    TypeRelation(String label, Orientation orientation) {
        this.label = label;
        this.orientation = orientation;
    }

    /**
     * Returns the label of the relationship.
     * 
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the orientation of the relationship.
     * 
     * @return The orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Enumeration defining the possible orientations for displaying relationships.
     */
    public enum Orientation {
        /** Horizontal orientation (for relationships at the same level) */
        HORIZONTAL,
        
        /** Vertical orientation (for hierarchical relationships) */
        VERTICAL
    }
}