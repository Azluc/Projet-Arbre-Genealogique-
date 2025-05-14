package com.cytech.classeProjet;


import java.util.*;


public enum NiveauVisibilite {
    public_,   // Visible par tous (note : on évite le mot réservé "public")
    protege,   // Visible uniquement par les membres de l’arbre
    prive      // Visible uniquement par l'utilisateur
}
