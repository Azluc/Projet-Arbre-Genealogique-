package com.cytech.classeProjet;

public class TestRelation {
    public static void main(String[] args) {
        Relation r = Relation.MERE;

        if (r.toString().equals("MERE")) {
            System.out.println("Test enum Relation MERE : OK");
        } else {
            System.out.println("Test enum Relation MERE : ÉCHEC");
        }

        if (Relation.ONCLE != null) {
            System.out.println("Test enum Relation ONCLE : OK");
        } else {
            System.out.println("Test enum Relation ONCLE : ÉCHEC");
        }
    }
}
