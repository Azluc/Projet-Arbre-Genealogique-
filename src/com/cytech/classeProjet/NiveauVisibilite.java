package com.cytech.classeProjet;

/**
 * Enumération représentant les niveaux de visibilité
 * possibles pour un arbre ou un nœud dans l'application.
 */
public enum NiveauVisibilite {
    public_("Public"),     // Visible par tous les utilisateurs
    protege("Protégé"),    // Visible uniquement par les membres de l’arbre
    prive("Privé");        // Visible uniquement par l'utilisateur lui-même

    private final String label;

    NiveauVisibilite(String label) {
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
     * Convertit un texte saisi en console ou interface en une valeur de l'enum.
     *
     * @param input texte comme "1", "public", "protégé", etc.
     * @return NiveauVisibilite correspondant
     * @throws IllegalArgumentException si aucun niveau ne correspond
     */
    public static NiveauVisibilite fromString(String input) {
        switch (input.toLowerCase()) {
            case "1":
            case "public":
            case "publique":
            case "public_":
                return public_;
            case "2":
            case "protégé":
            case "protege":
                return protege;
            case "3":
            case "privé":
            case "prive":
                return prive;
            default:
                throw new IllegalArgumentException("Niveau de visibilité inconnu : " + input);
        }
    }
}
