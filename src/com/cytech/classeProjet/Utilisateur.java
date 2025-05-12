package com.cytech.classeProjet;

import java.util.*;

/**
 * 
 */
public class Utilisateur extends Personne {
    private Double codePublic;
    private Double codePrive;
    private String email;
    private String motDePasse;
    private Double numeroSecuriteSociale;
    private Double telephone;
    private String adresse;
    /**
     * Default constructor
     */
    public Utilisateur() {
    	super();
    }
    public Utilisateur(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces
    		,Double codePublic,Double codePrive,String email, String motDePasse,Double numeroSecuriteSociale,Double telephone,
    		String adresse) {
    	super(nom, prenom, nationalite, dateNaissance, dateDeces);
    	this.codePublic=codePublic;
    	this.codePrive=codePrive
    	this.email = email;
    	this.motDePasse = motDePasse;
    	this.numeroSecuriteSociale = numeroSecuriteSociale;
    	this.telephone=telephone;
    	this.adresse=adresse;}	
    public Double getCodePublic() {
        // TODO implement here
    	return this.codePublic;
    }

    /**
     * 
     */
    public Double getCodePrive() {
        // TODO implement here
    	return this.codePrive;
    }

    /**
     * 
     */
    public String getMotDePasse() {
        // TODO implement here
    	return this.motDePasse;
    }

    /**
     * @param motDePasse
     */
    public void setMotDePasse(String motDePasse) {
        // TODO implement here
    	this.motDePasse=motDePasse;
    }

    /**
     * 
     */
    public String getEmail() {
        // TODO implement here
    	return this.email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        // TODO implement here
    	this.email=email;
    }

    /**
     * 
     */
    public Double getNumeroSecuriteSociale() {
        // TODO implement here
    	return this.numeroSecuriteSociale;
    }

    /**
     * 
     */
    public Double getTelephone() {
        // TODO implement here
    	return this.telephone
    }

    /**
     * @param telephone
     */
    public void setTelephone(double telephone) {
        // TODO implement here
    	this.telephone=telephone;
    }

    /**
     * 
     */
    public String getAdresse() {
        // TODO implement here
    	return this.adresse;
    }

    /**
     * @param adresse
     */
    public void setAdresse(String adresse) {
        // TODO implement here
    	this.adresse=adresse;
    }

    /**
     * @param codePublic 
     * @param codePrive 
     * @param email 
     * @param motDePasse 
     * @param numeroSecuriteSociale 
     * @param telephone 
     * @param adresse
     */
   
}