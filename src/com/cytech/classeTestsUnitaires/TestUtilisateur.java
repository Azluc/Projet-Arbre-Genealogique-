package com.cytech.classeProjet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUtilisateur {

    @Test
    public void testCreationUtilisateur() {
        Utilisateur u = new Utilisateur("Dupont", "Marie", "marie@exemple.com", "mdp123");

        assertEquals("Dupont", u.getNom());
        assertEquals("Marie", u.getPrenom());
        assertEquals("marie@exemple.com", u.getEmail());
        assertEquals("mdp123", u.getMotDePasse());
        assertFalse(u.isAdhesionValidee());
    }

    @Test
    public void testValidationAdhesion() {
        Utilisateur u = new Utilisateur("Durand", "Paul", "paul@mail.com", "pass");

        u.setAdhesionValidee(true);
        assertTrue(u.isAdhesionValidee());

        u.setAdhesionValidee(false);
        assertFalse(u.isAdhesionValidee());
    }

    @Test
    public void testToString() {
        Utilisateur u = new Utilisateur("Durand", "Alice", "alice@mail.com", "pass");
        assertTrue(u.toString().contains("Alice Durand - alice@mail.com"));
    }
}
