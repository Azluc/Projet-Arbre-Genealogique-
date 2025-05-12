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
    public Personne() {
    }

    /**
     * 
     */


    /**
     * @param nom 
     * @param prenom 
     * @param nationalite 
     * @param dateNaissance 
     * @param dateDeces
     */
    public Personne(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces) {
        // TODO implement here
    	this.nom=nom; 
    	this.prenom=prenom;
    	this.nationalite=nationalite
    	this.dateNaissance=dateNaissance;
    	this.dateDeces=dateDeces;
    }

    /**
     * 
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * 
     */
    public String getNom() {
        // TODO implement here
    	return this.nom;
    }

    /**
     * 
     */
    public String getNationalite() {
        // TODO implement here
    	return this.nationalite;
    }

    /**
     * @param nationalite
     */
    public void setNationalite(String nationalite) {
        // TODO implement here
    	this.nationalite=nationalite
    }

    /**
     * @param date
     */
    public void setDateNaissance(Date date) {
        // TODO implement here
    	this.dateNaissance=date
    }

    /**
     * @param date
     */
    public void setDateDeces(Date date) {
        // TODO implement here
    	this.dateDeces=date;
    }

}