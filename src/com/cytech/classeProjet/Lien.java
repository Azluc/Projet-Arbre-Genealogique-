package com.cytech.classeProjet;

 

public class Lien {
     private Personne personneSource;
    private Personne personneDestination;
    private TypeRelation type;
    private String nomRelation;
    
    
	@Override
	public String toString() {
		return "Lien [personneSource=" + personneSource + ", personneDestination=" + personneDestination + ", type="
				+ type + ", nomRelation=" + nomRelation + ", getPersonneSource()=" + getPersonneSource()
				+ ", getPersonneDestination()=" + getPersonneDestination() + ", getType()=" + getType()
				+ ", getNomRelation()=" + getNomRelation() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	public Lien(Personne personneSource, Personne personneDestination, TypeRelation type, String nomRelation) {
		super();
		this.personneSource = personneSource;
		this.personneDestination = personneDestination;
		this.type = type;
		this.nomRelation = nomRelation;
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
	public TypeRelation getType() {
		return type;
	}
	public void setType(TypeRelation type) {
		this.type = type;
	}
	public String getNomRelation() {
		return nomRelation;
	}
	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}
    
     
	 

     

    // getters, setters, equals, hashCode, toString...
}