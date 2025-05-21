package com.cytech.classeProjet;




public enum TypeRelation {
    FRERE_SOEUR("Frère/Sœur", Orientation.HORIZONTAL),
    PARENT_ENFANT("Parent-Enfant", Orientation.VERTICAL),
    UNION("Union", Orientation.HORIZONTAL);

    private final String label;
    private final Orientation orientation;

    TypeRelation(String label, Orientation orientation) {
        this.label = label;
        this.orientation = orientation;
    }

    public String getLabel() {
        return label;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
}