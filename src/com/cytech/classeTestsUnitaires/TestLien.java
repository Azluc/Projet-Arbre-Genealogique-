package com.cytech.classeTestsUnitaires;

import com.cytech.classeProjet.Genre;
import com.cytech.classeProjet.Lien;
import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;

import java.text.SimpleDateFormat;

public class TestLien {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Création de deux personnes
        Personne personne1 = new Personne("Martin", "Paul", sdf.parse("10/02/1970"), null, Genre.HOMME, 1, 1);
        Personne personne2 = new Personne("Martin", "Julien", sdf.parse("25/12/2000"), null, Genre.HOMME, 1, 2);

        // Création du lien entre les deux personnes
        Lien lien = new Lien(personne1, personne2, TypeRelation.PARENT_ENFANT, "Père");

        // Affichage du lien
        System.out.println("Test création de lien :");
        System.out.println("Personne source : " + lien.getPersonneSource().getNomComplet());
        System.out.println("Personne destination : " + lien.getPersonneDestination().getNomComplet());
        System.out.println("Type de relation : " + lien.getType());
        System.out.println("Nom de la relation : " + lien.getNomRelation());

        // Modification de la relation
        lien.setNomRelation("Père biologique");
        System.out.println("Nom de la relation modifié : " + lien.getNomRelation());

        // Test du toString()
        System.out.println("\nTest toString :");
        System.out.println(lien.toString());
    }
}
