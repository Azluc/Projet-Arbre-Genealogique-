package com.cytech.classeProjet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArbreGenealogique {
    private Personne racine;
    private int id_arbre;
    private Set<Personne> personnes;
      //private List<LienParente> liens = new ArrayList<>();
    private LienParente liensParente;
    
    
    
    public ArbreGenealogique() {
        this.liensParente = new LienParente(this);
    }
    // Getter et setter pour liensParente
    public LienParente getLiensParente() {
        return liensParente;
    }

    public void setLiensParente(LienParente liensParente) {
        this.liensParente = liensParente;
    }
 
	public ArbreGenealogique(Personne racine, int id_arbre, Set<Personne> personnes) {
		super();
		this.racine = racine;
		this.id_arbre = id_arbre;
		this.personnes = personnes;
		this.racine.setProfondeur(0);
		this.liensParente = new LienParente(this);
	}
	@Override
	public String toString() {
		return "ArbreGenealogique [racine=" + racine + ", id_arbre=" + id_arbre + ", personnes=" + personnes
				+ ", liensParente=" + liensParente + ", getLiensParente()=" + getLiensParente() + ", getRacine()="
				+ getRacine() + ", getId_arbre()=" + getId_arbre() + ", getPersonnes()=" + getPersonnes()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public Personne getRacine() {
		return racine;
	}
	public int getId_arbre() {
		return id_arbre;
	}
	public Set<Personne> getPersonnes() {
		return personnes;
	}
	
	public void supprimerSiFeuille(Personne p) {
	    if (p.getEnfants().isEmpty() || p.getParents().isEmpty()) {
	        // Supprimer les liens avec les parents
	        for (Personne parent : p.getParents()) {
	            parent.getEnfants().remove(p);
	        }

	        // Supprimer le lien avec le conjoint
	        if (p.getConjoint() != null) {
	            p.getConjoint().setConjoint(null);
	        }

	        // Supprimer de l'arbre
	        personnes.remove(p);  
	        System.out.println(p.getNomComplet() + " a été supprimé de l’arbre.");
	    } else {
	        System.out.println(p.getNomComplet() + " ne peut pas être supprimé : ce n’est pas une feuille.");
	    }
	}
	
	
	public static String ObtenirRelationsDepuisRacine(ArbreGenealogique arbre,Personne p) {
        if (!p.equals(arbre.getRacine())) {
        	String relationAscendante = arbre.getRacine().getRelationAscendanteAvec(p);
        	String relationDescendante = arbre.getRacine().getRelationDescendanteAvec(p);
        	String relationFrereSoeur = arbre.getRacine().getRelationLateralAvecFrereSoeur(p);
        	String relationCousin = arbre.getRacine().getRelationCousinAvec(p);
        	String relationDescendanteNieceNeveux = arbre.getRacine().getRelationNeveuAvec(p);
        	 
        	if (relationAscendante != null && relationDescendante == null && relationFrereSoeur == null && relationCousin == null && relationCousin == null) {
        		return relationAscendante;
        	}
        	
        	else if (relationDescendante != null && relationFrereSoeur == null && relationCousin == null && relationCousin == null) {
        		return relationDescendante;
        	}

        	else if (relationFrereSoeur != null && relationCousin == null && relationCousin == null) {
        	    return relationFrereSoeur;
        	    
        	} else if (relationCousin != null && relationDescendanteNieceNeveux == null) {
        	    return relationCousin;
        	    
        	} else if (relationDescendanteNieceNeveux != null) {
        	    return relationDescendanteNieceNeveux;
        	}else {
        	    return "inconnu";
        	}
        	 
        }
        else {
            return "racine";
        }     
	}

	
	public Personne trouverPersonneParNomPrenom(String nom, String prenom) {
	    for (Personne p : personnes) {
	        if (p.getNom().equalsIgnoreCase(nom) && p.getPrenom().equalsIgnoreCase(prenom)) {
	            return p;
	        }
	    }
	    return null;
	}
	
	public static void afficherRelations(ArbreGenealogique arbre) {
        System.out.println("\nRelations dans l'arbre depuis la racine :");
        Set<String> unionsAffichees = new HashSet<>();
        List<Personne> personnesASupprimer = new ArrayList<>();
        for (Personne p : arbre.getPersonnes()) {
            if (!p.equals(arbre.getRacine())) {
            	String relationAscendante = arbre.getRacine().getRelationAscendanteAvec(p);
            	String relationDescendante = arbre.getRacine().getRelationDescendanteAvec(p);
            	String relationFrereSoeur = arbre.getRacine().getRelationLateralAvecFrereSoeur(p);
            	String relationCousin = null;
            	String relationDescendanteNieceNeveux = null;

            	if (relationAscendante == null && relationDescendante == null) {
            		relationCousin = arbre.getRacine().getRelationCousinAvec(p);
            	}
            	
            	if (relationAscendante == null && relationDescendante == null && relationFrereSoeur == null) {
            		relationDescendanteNieceNeveux = arbre.getRacine().getRelationNeveuAvec(p);
            	}

            	if (relationAscendante != null) {
            	    System.out.println(p.getNomComplet() + " est " + relationAscendante + " de la racine." + " Il se situe du coté : "+p.getCote());
            	    
            	} else if (relationDescendante != null) {
            	    System.out.println(p.getNomComplet() + " est " + relationDescendante + " de la racine." + " Il se situe du coté : "+p.getCote());
            	    
            	} else if (relationFrereSoeur != null) {
            	    System.out.println(p.getNomComplet() + " est " + relationFrereSoeur + " de la racine." + " Il se situe du coté : "+p.getCote());
            	}else if (relationCousin != null) {
            	    System.out.println(p.getNomComplet() + " est " + relationCousin + " de la racine." + " Il se situe du coté : "+p.getCote());
            	}
            	else if (relationAscendante == null && relationDescendante == null && relationFrereSoeur == null && relationCousin == null && relationDescendanteNieceNeveux == null && !p.getEnfants().isEmpty()) {
            			personnesASupprimer.add(p); 
            		    System.out.println(p.getNomComplet() + " n'a pas de lien direct avec la racine.");
            		    arbre.supprimerSiFeuille(p);
            	}   
            }
            else {
                System.out.println(p.getNomComplet() + " est la racine.");
            }
        }
        // Suppression après boucle
        for (Personne p : personnesASupprimer) {
            arbre.supprimerSiFeuille(p);
        }
        // Afficher les couples  
        for (Personne p : arbre.getPersonnes()) {
            Personne conjoint = p.getConjoint();
            if (conjoint != null) {
                String idCouple = p.getNomComplet() + " & " + conjoint.getNomComplet();
                String idInverse = conjoint.getNomComplet() + " & " + p.getNomComplet();
                if (!unionsAffichees.contains(idCouple) && !unionsAffichees.contains(idInverse)) {
                    System.out.println(p.getNomComplet() + " et " + conjoint.getNomComplet() + " sont en couple.");
                    unionsAffichees.add(idCouple);
                }
            }
        }
        
    }
	public void setRacine(Personne racine) {
		this.racine = racine;
	}
	public void setId_arbre(int id_arbre) {
		this.id_arbre = id_arbre;
	}
	public void setPersonnes(Set<Personne> personnes) {
		this.personnes = personnes;
	}
	
	
}
