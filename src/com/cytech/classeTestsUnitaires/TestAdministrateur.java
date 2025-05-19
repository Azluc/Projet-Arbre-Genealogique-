package com.cytech.classeProjet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAdministrateur {

    private Administrateur admin;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        admin = new Administrateur("Admin", "Test", "admin@mail.com", "admin123");
        utilisateur = new Utilisateur("Doe", "John", "john@mail.com", "pass123");
    }

    @Test
    public void testConstructeur() {
        assertEquals("Admin", admin.getNom());
        assertEquals("Test", admin.getPrenom());
        assertEquals("admin@mail.com", admin.getEmail());
    }

    @Test
    public void testValiderAdhesion() {
        assertFalse(utilisateur.isAdhesionValidee());
        admin.validerAdhesion(utilisateur);
        assertTrue(utilisateur.isAdhesionValidee());
    }

    @Test
    public void testRefuserAdhesion() {
        utilisateur.setAdhesionValidee(true); // simulate an already validated account
        admin.refuserAdhesion(utilisateur);
        assertFalse(utilisateur.isAdhesionValidee());
    }
}
