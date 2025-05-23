package com.cytech.classeTestsUnitaires;

import com.cytech.classeProjet.Cote;

/**
 * Test class for Cote enum functionality.
 * This class contains unit tests for the Cote enum,
 * verifying the correct behavior of different family side values
 * (MATERNEL, NEUTRE) and their string representations.
 */
public class TestCote {
    /**
     * Main method to run all tests for Cote enum.
     * Tests the following aspects:
     * - MATERNEL value comparison
     * - NEUTRE value string representation
     */
    public static void main(String[] args) {
        Cote c = Cote.MATERNEL;

        if (c == Cote.MATERNEL) {
            System.out.println("Test Cote MATERNEL : OK");
        } else {
            System.out.println("Test Cote MATERNEL : ÉCHEC");
        }

        if (Cote.NEUTRE.toString().equals("NEUTRE")) {
            System.out.println("Test Cote NEUTRE : OK");
        } else {
            System.out.println("Test Cote NEUTRE : ÉCHEC");
        }
    }
}
