package com.cytech.classeTestsUnitaires;

import com.cytech.classeProjet.Genre;

/**
 * Test class for Genre enum functionality.
 * This class contains unit tests for the Genre enum,
 * verifying the correct behavior of gender values (HOMME, FEMME)
 * and their string representations.
 */
public class TestGenre {
    /**
     * Main method to run all tests for Genre enum.
     * Tests the following aspects:
     * - HOMME value comparison
     * - FEMME value string representation
     */
    public static void main(String[] args) {
        Genre g = Genre.HOMME;

        if (g == Genre.HOMME) {
            System.out.println("Test Genre HOMME : OK");
        } else {
            System.out.println("Test Genre HOMME : ÉCHEC");
        }

        if (Genre.FEMME.toString().equals("FEMME")) {
            System.out.println("Test Genre FEMME : OK");
        } else {
            System.out.println("Test Genre FEMME : ÉCHEC");
        }
    }
}
