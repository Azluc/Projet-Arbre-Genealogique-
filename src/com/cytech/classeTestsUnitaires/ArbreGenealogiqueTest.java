package com.cytech.classeProjet;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArbreGenealogiqueTest {
	/*
	 * Faire test Classique sans Junit avec des if et else
	 * */
    @Test
    public void testAjoutPersonne() {
        Personne racine = new Personne("Dupont", "Claire", "Française",
                new GregorianCalendar(1980, Calendar.JANUARY, 1).getTime(), null);

        ArbreGenealogique arbre = new ArbreGenealogique(1.0, NiveauVisibilite.public_, racine);

        Personne enfant = new Personne("Dupont", "Lucas", "Française",
                new GregorianCalendar(2010, Calendar.MARCH, 10).getTime(), null);

        arbre.ajouterPersonne(enfant);

        assertEquals(2, arbre.getPersonnes().size());
        assertTrue(arbre.getPersonnes().contains(enfant));
    }

    @Test
    public void testRecherchePersonneExistante() {
        Personne racine = new Personne("Durand", "Alice", "Française",
                new GregorianCalendar(1985, Calendar.JUNE, 15).getTime(), null);

        ArbreGenealogique arbre = new ArbreGenealogique(2.0, NiveauVisibilite.prive, racine);

        Personne result = arbre.rechercherPersonne("Durand", "Alice");

        assertNotNull(result);
        assertEquals("Durand", result.getNom());
    }

    @Test
    public void testChangerVisibilite() {
        Personne racine = new Personne("Martin", "Paul", "Française",
                new GregorianCalendar(1975, Calendar.APRIL, 5).getTime(), null);

        ArbreGenealogique arbre = new ArbreGenealogique(3.0, NiveauVisibilite.prive, racine);

        arbre.changerVisibiliteArbre(NiveauVisibilite.protege);

        assertEquals(NiveauVisibilite.protege, arbre.getVisibilite());
    }
}
