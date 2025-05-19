package com.cytech.classeProjet;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersonne {

    @Test
    public void testCreationPersonne() {
        Date naissance = new Date();
        Personne p = new Personne("Martin", "Lucie", "Française", naissance, null);

        assertEquals("Martin", p.getNom());
        assertEquals("Lucie", p.getPrenom());
        assertEquals("Française", p.getNationalite());
        assertEquals(naissance, p.getDateNaissance());
        assertNull(p.getDateDeces());
    }

    @Test
    public void testCoherenceDates_OK() throws Exception {
        Date naissance = new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime();
        Date deces = new GregorianCalendar(2050, Calendar.JANUARY, 1).getTime();

        assertTrue(Personne.verifierCoherenceDates(naissance, deces));
    }

    @Test
    public void testCoherenceDates_EncoreVivant() throws Exception {
        Date naissance = new GregorianCalendar(2000, Calendar.MARCH, 1).getTime();
        assertTrue(Personne.verifierCoherenceDates(naissance, null));
    }

    @Test
    public void testCoherenceDates_Erreur() {
        Date naissance = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        Date deces = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();

        Exception exception = assertThrows(Exception.class, () ->
                Personne.verifierCoherenceDates(naissance, deces));

        assertEquals("La date de naissance doit être avant la date de décès !", exception.getMessage());
    }
}
