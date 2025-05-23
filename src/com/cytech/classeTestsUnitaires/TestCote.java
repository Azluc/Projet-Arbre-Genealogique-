package com.cytech.classeTestsUnitaires;

import com.cytech.classeProjet.Cote;

public class TestCote {
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
