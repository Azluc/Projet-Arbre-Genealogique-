 
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


public class Personne {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private Date dateDeces;
    private Genre genre;
    private int id_arbre;
    private int profondeur;
    private List<Personne> parents;
    private List<Personne> enfants;
    private List<Personne> freresEtSoeurs;
    private Personne conjoint;
    private Cote cote = Cote.NEUTRE;
     private String nationalite;

     

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
    
    public Personne(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces) {
        // TODO implement here
    	this.nom=nom; 
    	this.prenom=prenom;
    	this.nationalite=nationalite;
    	this.dateNaissance=dateNaissance;
    	this.dateDeces=dateDeces;
    }
    
    public static Personne choisirPersonne(Set<Personne> personnes, String nom, String prenom) {
    	
    	
    	
        if (personnes.isEmpty()) {
            System.out.print("vide");
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
        //System.out.println("Personne non trouvée dans l'arbre. MethodeChoisirPersonne()");
        return null;
    }
     
    
    public void ajouterParent(Personne parent) {
    	if (!parent.getDateNaissance().before(this.getDateNaissance())) {
    	   // System.out.println("Erreur : Le parent (" + parent.getNomComplet() + ") doit être né avant l'enfant (" + this.getNomComplet() + ").");
    	    return;
    	}
    	
        if (parents.contains(parent)) return;

        if (parents.size() < 2) {
            parents.add(parent);
            if (parent.getGenre() == Genre.HOMME) {
            	parent.setCote(Cote.PATERNEL);
            }
            else {
            	parent.setCote(Cote.MATERNEL);
            }

            // Mise à jour des parents des frères et sœurs
            for (Personne frereSoeur : this.getFreresEtSoeurs()) {
                if (!frereSoeur.getParents().contains(parent) && frereSoeur.getParents().size() < 2) {
                    frereSoeur.ajouterParent(parent);
                }
            }

            // Vérification automatique de l’union
            if (parents.size() == 2) {
                Personne autreParent = (parents.get(0).equals(parent)) ? parents.get(1) : parents.get(0);

                if (parent.getConjoint() == null && autreParent.getConjoint() == null) {
                    parent.setConjoint(autreParent);
                    autreParent.setConjoint(parent);
                    System.out.println("Union détectée entre " + parent.getNomComplet() + " et " + autreParent.getNomComplet());
                }
            }
        } else {
            System.out.println("Erreur : Impossible d’ajouter plus de deux parents à " + this.getNomComplet());
        }
    }
    
    public void ajouterEnfant(Personne enfant, ArbreGenealogique arbre) {
    	if (!this.getDateNaissance().before(enfant.getDateNaissance())) {
    	    System.out.println("Erreur : L'enfant (" + enfant.getNomComplet() + ") doit être né après le parent (" + this.getNomComplet() + ").");
    	    return;
    	}
        if (!enfants.contains(enfant)) {
            enfants.add(enfant);
            enfant.ajouterParent(this);
         // Ici on ajoute le lien parent-enfant dans l'arbre
            String nomRelation = ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, enfant);
            arbre.getLiensParente().ajouterLien(this, enfant, TypeRelation.PARENT_ENFANT, nomRelation);
        }
    }
 
    public void ajouterFrereOuSoeur(Personne autre, ArbreGenealogique arbre) {
        if (!freresEtSoeurs.contains(autre)) {
            freresEtSoeurs.add(autre);
            autre.ajouterFrereOuSoeur(this, arbre); // lien réciproque

            autre.setProfondeur(this.getProfondeur());

            for (Personne parent : this.getParents()) {
                if (!autre.getParents().contains(parent)) {
                    autre.ajouterParent(parent);
                    parent.ajouterEnfant(autre, arbre);

                    // Ajout du lien dans l'arbre
                    String nomRelation = ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, autre);
                    arbre.getLiensParente().ajouterLien(parent, autre, TypeRelation.PARENT_ENFANT, nomRelation);
                }
            }

             
            for (Personne frereOuSoeur : this.freresEtSoeurs) {
                if (!frereOuSoeur.equals(autre) && !frereOuSoeur.getFreresEtSoeurs().contains(autre)) {
                    frereOuSoeur.ajouterFrereOuSoeur(autre, arbre);
                }
            }
        }
    }

     

    public String getRelationAscendanteAvec(Personne cible) {
        int diff = cible.getProfondeur() - this.getProfondeur();

        if (diff <= 0) return null;

        // Cas direct : parent
        if (diff == 1 && this.getParents().contains(cible)) {
            return cible.getGenre() == Genre.HOMME ? "père" : "mère";
        }

        // Cas direct : oncle/tante
        if (diff == 1) {
            for (Personne parent : this.getParents()) {
                if (parent.getFreresEtSoeurs().contains(cible)) {
                    return cible.getGenre() == Genre.HOMME ? "oncle" : "tante";
                }
            }
        }

        // Cas : grand-parent
        if (diff == 2 && this.getParents().stream().anyMatch(p -> p.getParents().contains(cible))) {
            return cible.getGenre() == Genre.HOMME ? "grand-père" : "grand-mère";
        }

        // Cas : frère/soeur d'un grand-parent (oncle ou tante éloigné·e)
        if (diff == 2) {
            for (Personne parent : this.getParents()) {
                for (Personne grandParent : parent.getParents()) {
                    if (grandParent.getFreresEtSoeurs().contains(cible)) {
                        return cible.getGenre() == Genre.HOMME ? "grand-oncle" : "grand-tante";
                    }
                }
            }
        }
        
        
        
        // Cas général : arrière-grand-[x]
        if (diff > 2) {
            int nbArriere = diff - 2;
            String prefixe = "arrière-".repeat(nbArriere);
            boolean estParent = this.estAscendant(cible);
            boolean estFrereOuSoeurDUnAscendant = this.estFrereOuSoeurDUnAscendant(cible);

            if (estParent) {
                return cible.getGenre() == Genre.HOMME ? prefixe + "grand-père" : prefixe + "grand-mère";
            }

            if (estFrereOuSoeurDUnAscendant) {
                return cible.getGenre() == Genre.HOMME ? prefixe + "grand-oncle" : prefixe + "grand-tante";
            }
        }

        return null;
    }
    
    public String getRelationDescendanteAvec(Personne cible) {
        int diff = cible.getProfondeur() - this.getProfondeur();

        if (diff >= 0) return null; // Ce n’est pas un descendant

        // Cas direct : enfant
        if (diff == -1 && this.getEnfants().contains(cible)) {
            return cible.getGenre() == Genre.HOMME ? "fils" : "fille";
        }

        // Cas direct : petit-enfant
        if (diff == -2) {
            for (Personne enfant : this.getEnfants()) {
                if (enfant.getEnfants().contains(cible)) {
                    return cible.getGenre() == Genre.HOMME ? "petit-fils" : "petite-fille";
                }
            }
        }

        // Cas général : arrière-petit-[x]
        if (diff < -2) {
            int nbArriere = Math.abs(diff + 2);
            String prefixe = "arrière-".repeat(nbArriere);

            if (this.estDescendant(cible)) {
                return cible.getGenre() == Genre.HOMME ? prefixe + "petit-fils" : prefixe + "petite-fille";
            }
        }

        return null;
    }
    
    
    
    
    
    public boolean estDescendant(Personne cible) {
        for (Personne enfant : this.getEnfants()) {
            if (enfant.equals(cible) || enfant.estDescendant(cible)) {
                return true;
            }
        }
        return false;
    } 
    
    private boolean estAscendant(Personne cible) {
        for (Personne parent : this.getParents()) {
            if (parent.equals(cible)) return true;
            if (parent.estAscendant(cible)) return true;
        }
        return false;
    }
    
    public String getRelationCousinAvec(Personne cible) {
        int diff = cible.getProfondeur() - this.getProfondeur();

        // Cas classique : cousin/cousine (même profondeur)
        if (diff == 0) {
            for (Personne parent : this.getParents()) {
                for (Personne frereOuSoeur : parent.getFreresEtSoeurs()) {
                    if (frereOuSoeur.getEnfants().contains(cible)) {
                        return cible.getGenre() == Genre.HOMME ? "cousin" : "cousine";
                    }
                }
            }
        }

        // Cas : petit-cousin/petite-cousine (profondeur -1)
        if (diff == -1) {
            for (Personne parent : cible.getParents()) {
                String relationAvecParent = this.getRelationCousinAvec(parent);
                if (relationAvecParent != null &&
                    (relationAvecParent.equals("cousin") || relationAvecParent.equals("cousine"))) {
                    return cible.getGenre() == Genre.HOMME ? "petit-cousin" : "petite-cousine";
                }
            }
        }

        // Cas général : arrière-petit-[x]-cousin
        if (diff < -1) {
            for (Personne parent : cible.getParents()) {
                String relationAvecParent = this.getRelationCousinAvec(parent);
                if (relationAvecParent != null &&
                    (relationAvecParent.contains("petit-cousin") || relationAvecParent.contains("petite-cousine"))) {

                    int nbArriere = Math.abs(diff + 1);
                    String prefixe = "arrière-".repeat(nbArriere);
                    return cible.getGenre() == Genre.HOMME
                            ? prefixe + "petit-cousin"
                            : prefixe + "petite-cousine";
                }
            }
        }

        return null;
    }

    
    public String getRelationNeveuAvec(Personne cible) {
        int diff = cible.getProfondeur() - this.getProfondeur();

        // Cas classique : neveux ou niece de la même profondeur
        if (diff == -1) {
            for (Personne frereOuSoeur : this.getFreresEtSoeurs()) {
                
                    if (frereOuSoeur.getEnfants().contains(cible)) {
                        return cible.getGenre() == Genre.HOMME ? "neveu" : "nièce";
                     
                }
            }
        }

        // Cas : petit-cousin/petite-cousine (profondeur -1)
        if (diff == -2) {
            for (Personne frereOuSoeur : this.getFreresEtSoeurs()) {
                for (Personne enfant : frereOuSoeur.getEnfants()) {
                    if (enfant.getEnfants().contains(cible)) {
                        return cible.getGenre() == Genre.HOMME ? "petit-neveu" : "petite-nièce";
                    }
                }
            }
        }

        // Cas général : arrière-petit-[x]-neveux/niece
        if (diff <= -3) {
            Queue<Personne> file = new LinkedList<>();
            for (Personne frereOuSoeur : this.getFreresEtSoeurs()) {
                file.addAll(frereOuSoeur.getEnfants());
            }

            int niveau = 2; // petit-neveu/nièce = 2, arrière-petit-neveu/nièce = 3, etc.

            while (!file.isEmpty()) {
                int tailleNiveau = file.size();
                for (int i = 0; i < tailleNiveau; i++) {
                    Personne actuel = file.poll();
                    if (actuel.equals(cible)) {
                        StringBuilder prefixe = new StringBuilder();
                        for (int j = 3; j <= niveau; j++) {
                            prefixe.append("arrière-");
                        }
                        return prefixe + (cible.getGenre() == Genre.HOMME ? "petit-neveu" : "petite-nièce");
                    }
                    file.addAll(actuel.getEnfants());
                }
                niveau++;
            }
        }

        return null;
    }

    public void setNom(String nom) {
		this.nom = nom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public void setId_arbre(int id_arbre) {
		this.id_arbre = id_arbre;
	}



	public void setFreresEtSoeurs(List<Personne> freresEtSoeurs) {
		this.freresEtSoeurs = freresEtSoeurs;
	}



	public static boolean aAuMoins18AnsDePlus(Date plusVieux, Date plusJeune) {
    	//POur vérifier que la différence d'âge entre 2 un parent et un enfant est au moins de 18 ans
        Calendar calVieux = Calendar.getInstance();
        calVieux.setTime(plusVieux);
        
        Calendar calJeune = Calendar.getInstance();
        calJeune.setTime(plusJeune);
      
        calVieux.add(Calendar.YEAR, 18); // Ajoute 18 ans au plus vieux
        //On retourne vrai si le plus vieux a au moins 18 ans de plus 
        return !calVieux.after(calJeune);  
    }
    
    private boolean estFrereOuSoeurDUnAscendant(Personne cible) {
        for (Personne parent : this.getParents()) {
            if (parent.getFreresEtSoeurs().contains(cible)) return true;
            if (parent.estFrereOuSoeurDUnAscendant(cible)) return true;
        }
        return false;
    }
    
     
    
    public String getRelationLateralAvecFrereSoeur(Personne autre) {
        if (this.equals(autre) || this.estParentDe(autre) || autre.estParentDe(this)) {
            return null;
        }

        //	System.out.println("On est dans le GetRElationLateral et this vaut" + this.getNom() + "P vaut " +autre.getNom());
        // Frère ou sœur
        if (this.getFreresEtSoeurs().contains(autre)) {
            return autre.getGenre() == Genre.HOMME ? "frère" : "sœur";
        }
        return null;
    }
    
	    
 

    public boolean unionEstPossible(Personne personneSource, Personne personneDestination) {
        if (personneSource == null || personneDestination == null) return false;
        for (Personne enfant : personneSource.getEnfants()) {
            if (personneDestination.getEnfants().contains(enfant)) {
                return true;
            }
        }
        return false;
    }

    public boolean unionEstPossible(Personne enfant) {
        if (enfant == null) return false;
        List<Personne> parents = enfant.getParents();
        if (parents.size() != 2) return false;

        boolean possedePere = parents.stream().anyMatch(p -> p.getGenre() == Genre.HOMME);
        boolean possedeMere = parents.stream().anyMatch(p -> p.getGenre() == Genre.FEMME);
        return possedePere && possedeMere;
    }

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
    
    public boolean estIdentique(Personne autre) {
        return this.nom.equalsIgnoreCase(autre.nom) && this.prenom.equalsIgnoreCase(autre.prenom);
    }
    
    

    public Personne getPere() {
        for (Personne parent : parents) {
            if (parent.getGenre() == Genre.HOMME) return parent;
        }
        return null;
    }

    public Personne getMere() {
        for (Personne parent : parents) {
            if (parent.getGenre() == Genre.FEMME) return parent;
        }
        return null;
    }

    public List<Personne> getOnclesEtTantes() {
        List<Personne> resultats = new ArrayList<>();
        for (Personne parent : parents) {
            resultats.addAll(parent.getFreresEtSoeurs());
        }
        return resultats;
    }
 
    
    public boolean estParentDe(Personne enfant) {
        return enfant.getParents().contains(this);
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personne)) return false;
        Personne personne = (Personne) o;
        return nom.equalsIgnoreCase(personne.nom) && prenom.equalsIgnoreCase(personne.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom.toLowerCase(), prenom.toLowerCase());
    }
    
    public static boolean verifierCoherenceDates(Date dateNaissance, Date dateDeces) {
        if (dateNaissance == null && dateDeces == null) {
            // Cas ou l'utilisateur n'indique ni la date de naissance ni la date de deces d'une personne
        	return false;
        }
        
        if (dateNaissance != null && dateDeces == null) {
        	// Cas ou l'utilisateur est toujours en vie
            return true;
        }
        
        if (dateNaissance.before(dateDeces)) {
        	// Cas ou l'utilisateur indique que la date de naissance est avant la date de deces 
            return true;
        } else {
        	// si la date de deces est avant la date de naissance
            return false;
        }
    }
   
   

    // Getters / Setters
    public String getNomComplet() {
        return nom + " " + prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(Date dateDeces) {
        this.dateDeces = dateDeces;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getId_arbre() {
        return id_arbre;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

    public List<Personne> getParents() {
        return parents;
    }

    public void setParents(List<Personne> parents) {
        this.parents = parents;
    }

    public List<Personne> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Personne> enfants) {
        this.enfants = enfants;
    }

    public List<Personne> getFreresEtSoeurs() {
        return freresEtSoeurs;
    }
    
    public Cote getCote() {
        return cote;
    }

    public void setCote(Cote cote) {
        this.cote = cote;
    }
     
    public Personne getConjoint() {
        return conjoint;
    }

    public void setConjoint(Personne conjoint) {
        this.conjoint = conjoint;
    }
    
    public String getNationalite() {
        // TODO implement here
    	return this.nationalite;
    }

    @Override
    public String toString() {
        return this.getPrenom() + " " + this.getNom();  
    }
    
    public void setNationalite(String nationalite) {
        // TODO implement here
    	this.nationalite=nationalite;
    }
    
}