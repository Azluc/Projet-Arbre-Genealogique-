package com.cytech.classeProjet;

/**
 * Enumération des types de relations dans un arbre généalogique.
 */
public enum TypeRelation {
    ascendant("Ascendant"),
    descendant("Descendant"),
    collateral("Collatéral"),
    union("Union");

    private final String label;

    TypeRelation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * Convertit une chaîne (ex: "ascendant", "1", etc.) en TypeRelation.
     *
     * @param input texte saisi
     * @return TypeRelation correspondant
     * @throws IllegalArgumentException si non reconnu
     */
    public static TypeRelation fromString(String input) {
        switch (input.toLowerCase()) {
            case "1":
            case "ascendant":
                return ascendant;
            case "2":
            case "descendant":
                return descendant;
            case "3":
            case "collateral":
            case "collatéral":
                return collateral;
            case "4":
            case "union
