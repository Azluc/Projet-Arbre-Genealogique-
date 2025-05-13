package com.cytech.classeProjet;
import java.util.*;

/**
 * 
 */
public class Personne {

    private String nom;
    private String prenom;
    private String nationalite;
    private Date dateNaissance;
    private Date dateDeces;
   

   
    public Personne(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces) {
        // TODO implement here
    	this.nom=nom; 
    	this.prenom=prenom;
    	this.nationalite=nationalite;
    	this.dateNaissance=dateNaissance;
    	this.dateDeces=dateDeces;
    }

    public static boolean verifierCoherenceDates(Date dateNaissance, Date dateDeces) throws Exception {
        if (dateNaissance == null && dateDeces == null) {
            // Cas ou l'utilisateur n'indique ni la date de naissance ni la date de deces d'une personne
        	throw new Exception("Les dates ne doivent pas être nulles.");
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
            throw new Exception("La date de naissance doit être avant la date de décès !");
        }
    }
   
    public String getPrenom() {
        return this.prenom;
    }

   
    public String getNom() {
        // TODO implement here
    	return this.nom;
    }

    
    public String getNationalite() {
        // TODO implement here
    	return this.nationalite;
    }

   
    public void setNationalite(String nationalite) {
        // TODO implement here
    	this.nationalite=nationalite;
    }

    
    public void setDateNaissance(Date dateNaissance) {
        // TODO implement here
    	this.dateNaissance=dateNaissance;
    }

    /**
     * @param date
     */
    public void setDateDeces(Date dateDeces) {
        // TODO implement here
    	this.dateDeces=dateDeces;
    }


	public Date getDateNaissance() {
		return dateNaissance;
	}


	public Date getDateDeces() {
		return dateDeces;
	}

}