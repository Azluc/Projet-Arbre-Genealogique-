package com.cytech.classeProjet;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestLienParente {

    @Test
    public void testCreationLienParente() {
        Personne source = new Personne("Dupont", "Alice", "Française", new Date(), null);
        Personne cible = new Personne("Dupont", "Louis", "Française", new Date(), null);
        TypeRelation type = TypeRelation.collateral;
        double profondeur = 2.0;

        LienParente lien = new LienParente(source, cible, type, profondeur);

        assertEquals(source, lien.getSource());
        assertEquals(cible, lien.getCible());
        assertEquals(type, lien.getTypeRelation());
        assertEquals(2.0, lien.getProfondeur());
    }

    @Test
    public void testToStringLienParente() {
        Personne source = new Personne("Martin", "Julie", "Française", new Date(), null);
        Personne cible = new Personne("Martin", "Tom", "Française", new Date(), null);
        LienParente lien = new LienParente(source, cible, TypeRelation.descendant, 1.0);

        String expected = "Julie → Descendant → Tom (niveau : 1.0)";
        assertEquals(expected, lien.toString());
    }
}
