package com.cytech.classeProjet;

import java.util.Date;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String nationalite;
    private Date dateNaissance;
    private Date dateDeces;

    public Personne(int id, String nom, String prenom, String nationalite, Date dateNaissance, Date dateDeces) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.dateNaissance = dateNaissance;
        this.dateDeces = dateDeces;
    }

    public static boolean verifierCoherenceDates(Date dateNaissance, Date dateDeces) throws Exception {
        if (dateNaissance == null && dateDeces == null) {
            throw new Exception("Les dates ne doivent pas être nulles.");
        }
        if (dateNaissance != null && dateDeces == null) return true;
        if (dateNaissance.before(dateDeces)) return true;
        throw new Exception("La date de naissance doit être avant la date de décès !");
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }

    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }

    public Date getDateDeces() { return dateDeces; }
    public void setDateDeces(Date dateDeces) { this.dateDeces = dateDeces; }
}
