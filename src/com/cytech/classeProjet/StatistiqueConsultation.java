package com.cytech.classeProjet;


public class StatistiqueConsultation {

    private double nombreConsultation;
    private Utilisateur consultant;

    public StatistiqueConsultation(double nombreConsultation, Utilisateur consultant) {
        this.nombreConsultation = nombreConsultation;
        this.consultant = consultant;
    }

    public void augmenterConsultation(double nombre) {
        this.nombreConsultation += nombre;
    }

    public double getNombreConsultation() {
        return nombreConsultation;
    }

    public Utilisateur getConsultant() {
        return consultant;
    }

    public void setNombreConsultation(double nombreConsultation) {
        this.nombreConsultation = nombreConsultation;
    }

    public void setConsultant(Utilisateur consultant) {
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "Consultations
