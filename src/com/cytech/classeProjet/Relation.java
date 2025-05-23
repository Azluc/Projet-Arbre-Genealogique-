package com.cytech.classeProjet;

/**
 * Enumeration defining all possible relationships that can exist between people in the genealogical tree.
 * This includes immediate family relationships, extended family relationships, and marital relationships.
 */
public enum Relation {
    /** Father */
    PERE,
    /** Mother */
    MERE,
    /** Brother */
    FRERE,
    /** Sister */
    SOEUR,
    /** Uncle */
    ONCLE,
    /** Aunt */
    TANTE,
    /** Male cousin */
    COUSIN,
    /** Female cousin */
    COUSINE,
    /** Male second cousin */
    PETIT_COUSIN,
    /** Female second cousin */
    PETITE_COUSINE,
    /** Nephew */
    NEVEU,
    /** Niece */
    NIECE,
    /** Grandnephew */
    PETIT_NEVEU,
    /** Grandniece */
    PETITE_NIECE,
    /** Grandfather */
    GRAND_PERE,
    /** Grandmother */
    GRAND_MERE,
    /** Son */
    FILS,
    /** Daughter */
    FILLE,
    /** Granddaughter */
    PETITE_FILLE,
    /** Grandson */
    PETIT_FILS,
    /** Great-grandson */
    ARRIERE_PETIT_FILS,
    /** Great-granddaughter */
    ARRIERE_PETITE_FILLE,
    /** Great-grandfather */
    ARRIERE_GRAND_PERE,
    /** Great-grandmother */
    ARRIERE_GRAND_MERE,
    /** Husband */
    EPOUX,
    /** Wife */
    EPOUSE
}