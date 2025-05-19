package com.cytech.classeProjet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NiveauVisibiliteTest {

    @Test
    public void testToString() {
        assertEquals("Public", NiveauVisibilite.public_.toString());
        assertEquals("Protégé", NiveauVisibilite.protege.toString());
        assertEquals("Privé", NiveauVisibilite.prive.toString());
    }

    @Test
    public void testFromStringTexte() {
        assertEquals(NiveauVisibilite.public_, NiveauVisibilite.fromString("public"));
        assertEquals(NiveauVisibilite.protege, NiveauVisibilite.fromString("protégé"));
        assertEquals(NiveauVisibilite.prive, NiveauVisibilite.fromString("privé"));
    }

    @Test
    public void testFromStringChiffres() {
        assertEquals(NiveauVisibilite.public_, NiveauVisibilite.fromString("1"));
        assertEquals(NiveauVisibilite.protege, NiveauVisibilite.fromString("2"));
        assertEquals(NiveauVisibilite.prive, NiveauVisibilite.fromString("3"));
    }

    @Test
    public void testFromStringInvalide() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NiveauVisibilite.fromString("autre"));
        assertTrue(exception.getMessage().contains("Niveau de visibilité inconnu"));
    }
}
