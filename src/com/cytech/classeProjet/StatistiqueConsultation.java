package com.cytech.classeProjet;

public class StatistiqueConsultation {

    private int nombreConsultation;
    private Utilisateur consultant;

    public StatistiqueConsultation(int nombreConsultation, Utilisateur consultant) {
        this.nombreConsultation = nombreConsultation;
        this.consultant = consultant;
    }

    // Incrémente le compteur de consultations
    public void augmenterConsultation(int nombre) {
        if (nombre > 0) {
            this.nombreConsultation += nombre;
        }
    }

    // Getters
    public int getNombreConsultation() {
        return nombreConsultation;
    }

    public Utilisateur getConsultant() {
        return consultant;
    }

    // Setters
    public void setNombreConsultation(int nombreConsultation) {
        this.nombreConsultation = nombreConsultation;
    }

    public void setConsultant(Utilisateur consultant) {
        this.consultant = consultant;
    }

    // toString amélioré
    @Override
    public String toString() {
        return "Consultant : " + consultant.getNom() + ", Nombre de consultations : " + nombreConsultation;
    }
}
