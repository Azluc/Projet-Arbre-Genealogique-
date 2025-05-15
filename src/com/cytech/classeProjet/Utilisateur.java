package com.cytech.classeProjet;

import java.util.Date;

public class Utilisateur extends Personne {
    private Double codePublic;
    private Double codePrive;
    private String email;
    private String motDePasse;
    private Double numeroSecuriteSociale;
    private String telephone;
    private String adresse;
    

    public Utilisateur(String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces,
                       Double codePublic, Double codePrive, String email, String motDePasse,
                       Double numeroSecuriteSociale, String telephone, String adresse) {
        super(nom, prenom, nationalite, dateNaissance, dateDeces);
        this.codePublic = codePublic;
        this.codePrive = codePrive;
        this.email = email;
        this.motDePasse = motDePasse;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.telephone = telephone;
        this.adresse = adresse;
     
    }

    public Double getCodePublic() {
        return codePublic;
    }

    public Double getCodePrive() {
        return codePrive;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getNumeroSecuriteSociale() {
        return numeroSecuriteSociale;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone =telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }}
