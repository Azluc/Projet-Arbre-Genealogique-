package com.cytech.classeProjet;

 

import java.util.ArrayList;

import java.util.List;
 

public class LienParente {
    
     private ArbreGenealogique arbre;   // référence à l'arbre
    private List<Lien> liens;  // liste de liens individuels

    public LienParente(ArbreGenealogique arbre) {
        this.arbre = arbre;
        this.liens = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "LienParente [arbre=" + arbre + ", liens=" + liens + ", getLiens()=" + getLiens() + ", getArbre()="
                + getArbre() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

    /**
     * Ajoute un lien de parenté à l'arbre généalogique en évitant les doublons.
     * Un lien est considéré comme doublon s'il a la même source, destination et type de relation.
     * Si un doublon est trouvé, le lien existant est mis à jour avec le nouveau nom de relation si nécessaire.
     */
    public void ajouterLien(Personne p1, Personne p2, TypeRelation typeRelation, String nomRelation) {
        // Vérifier si un lien similaire existe déjà
        for (Lien lien : liens) {
            if (lienSimilaire(lien, p1, p2, typeRelation)) {
                // Si un lien similaire existe mais le nom de relation est différent, mettre à jour
                if (!lien.getNomRelation().equals(nomRelation)) {
                    lien.setNomRelation(nomRelation);
                    System.out.println("Mise à jour du lien existant : " + p1.getNomComplet() + " --(" 
                                      + typeRelation + ", " + nomRelation + ")--> " + p2.getNomComplet());
                }
                return; // Sortir car un lien similaire a été trouvé et traité
            }
        }
        
        // Si on arrive ici, aucun lien similaire n'existe, donc on l'ajoute
        System.out.println("Ajout du lien : " + p1.getNomComplet() + " --(" 
                          + typeRelation + ", " + nomRelation + ")--> " + p2.getNomComplet());
        liens.add(new Lien(p1, p2, typeRelation, nomRelation));
    }
    
    /**
     * Vérifie si un lien est similaire (même source, destination et type) au lien spécifié
     */
    private boolean lienSimilaire(Lien lien, Personne source, Personne destination, TypeRelation type) {
        return lien.getPersonneSource().equals(source) &&
               lien.getPersonneDestination().equals(destination) &&
               lien.getType() == type;
    }
    
    /**
     * Nettoie les liens en supprimant les doublons.
     * Cette méthode peut être appelée pour nettoyer la liste des liens existants.
     */
    public void nettoyerDoublons() {
        List<Lien> liensUniques = new ArrayList<>();
        
        for (Lien lien : liens) {
            boolean estUnique = true;
            
            // Vérifie si ce lien existe déjà dans notre liste de liens uniques
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
        
        // Remplacer l'ancienne liste par la liste sans doublons
        this.liens = liensUniques;
        System.out.println("Nettoyage des liens terminé. " + liens.size() + " liens uniques conservés.");
    }

    public List<Lien> getLiens() {
        return liens;
    }

    public ArbreGenealogique getArbre() {
        return arbre;
    }

    public void setArbre(ArbreGenealogique arbre) {
        this.arbre = arbre;
    }

    public void setLiensParente(List<Lien> liens) {
        this.liens = liens;
    }
}
