package com.cytech.classeProjet;

public class LienParente {

    private Personne personneSource;
    private Personne personneDestination;
    private TypeRelation typeRelation;
    private Double profondeur;

    public LienParente(Personne personneSource, Personne personneDestination, TypeRelation typeRelation, Double profondeur) {
        this.personneSource = personneSource;
        this.personneDestination = personneDestination;
        this.typeRelation = typeRelation;
        this.profondeur = profondeur;
    }

    public Personne getPersonneSource() {
        return personneSource;
    }

    public void setPersonneSource(Personne personneSource) {
        this.personneSource = personneSource;
    }

    public Personne getPersonneDestination() {
        return personneDestination;
    }

    public void setPersonneDestination(Personne personneDestination) {
        this.personneDestination = personneDestination;
    }

    public TypeRelation getTypeRelation() {
        return typeRelation;
    }

    public void setTypeRelation(TypeRelation typeRelation) {
        this.typeRelation = typeRelation;
    }

    public Double getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(Double profondeur) {
        this.profondeur = profondeur;
    }
}
