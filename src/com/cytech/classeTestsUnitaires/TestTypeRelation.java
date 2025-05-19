package com.cytech.classeProjet;

public class TestTypeRelation_Console {
    public static void main(String[] args) {

        if (TypeRelation.ascendant.getLabel().equals("Ascendant")) {
            System.out.println("Test 1 - getLabel ascendant : OK");
        } else {
            System.out.println("Test 1 - getLabel ascendant : ÉCHEC");
        }

        // Test 2 : toString()
        if (TypeRelation.collateral.toString().equals("Collatéral")) {
            System.out.println("Test 2 - toString collateral : OK");
        } else {
            System.out.println("Test 2 - toString collateral : ÉCHEC");
        }


        try {
            TypeRelation r = TypeRelation.fromString("union");
            if (r == TypeRelation.union) {
                System.out.println("Test 3 - fromString 'union' : OK");
            } else {
                System.out.println("Test 3 - fromString 'union' : ÉCHEC");
            }
        } catch (Exception e) {
            System.out.println("Test 3 - fromString 'union' : ÉCHEC (exception)");
        }


        try {
            TypeRelation r = TypeRelation.fromString("3");
            if (r == TypeRelation.collateral) {
                System.out.println("Test 4 - fromString '3' : OK");
            } else {
                System.out.println("Test 4 - fromString '3' : ÉCHEC");
            }
        } catch (Exception e) {
            System.out.println("Test 4 - fromString '3' : ÉCHEC (exception)");
        }


        try {
            TypeRelation.fromString("inconnu");
            System.out.println("Test 5 - fromString invalide : ÉCHEC (aucune exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 - fromString invalide : OK (exception capturée)");
        }
    }
}
