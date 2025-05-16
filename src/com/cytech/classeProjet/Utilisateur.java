package com.cytech.classeProjet;

import java.util.Date;

public class Utilisateur extends Personne {
	
	@SuppressWarnings("unused")
	private String nom;
	@SuppressWarnings("unused")
	private String prenom;
	@SuppressWarnings("unused")
	private String nationalite;
    private Double codePublic;
    
	private Double codePrive;
    private String email;
    private String motDePasse;
    private Double numeroSecuriteSociale;
    private String telephone;
    private String adresse;
    @SuppressWarnings("unused")
	private Date dateNaissance;
    
    
	public Utilisateur(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces,
		    Double codePublic, Double codePrive, String email, String motDePasse,
			Double numeroSecuriteSociale, String telephone, String adresse) {
		super(nom, prenom, nationalite, dateNaissance, null);
		this.codePublic = codePublic;
		this.codePrive = codePrive;
		this.email = email;
		this.motDePasse = motDePasse;
		this.numeroSecuriteSociale = numeroSecuriteSociale;
		this.telephone = telephone;
		this.adresse = adresse;

	}
	public String getNationalite() {
		return super.getNationalite();
	}
	public void setNationalite(String nationalite) {
		super.setNationalite(nationalite);
	}
	public Double getCodePublic() {
		return this.codePublic;
	}
 
	public Double getCodePrive() {
		return this.codePrive;
	}
 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public Double getNumeroSecuriteSociale() {
		return this.numeroSecuriteSociale;
	}
 
	public String getTelephone() {
		return this.telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Date getDateNaissance() {
		return super.getDateNaissance();
	}
	public void setDateNaissance(Date dateNaissance) {
		super.setDateNaissance(dateNaissance);
	}
	public String getNom() {
		return super.getNom();
	}
	public String getPrenom() {
		return super.getPrenom();
	}
	 
	 
 

 
}
