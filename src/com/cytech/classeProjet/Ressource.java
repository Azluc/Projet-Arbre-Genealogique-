package com.cytech.classeProjet;

import java.time.LocalDateTime;

public class Ressource {

    private TypeDocument typeDocument;
    private String contenu; // peut être un lien vers une image, un texte, etc.
    private LocalDateTime datePartage; // facultatif mais utile

    // Constructeur
    public Ressource(TypeDocument typeDocument, String contenu) {
        this.typeDocument = typeDocument;
        this.contenu = contenu;
        this.datePartage = LocalDateTime.now(); // Date automatique à la création
    }

    // Méthode pour simuler le partage
    public void partager() {
        System.out.println("Partage de " + typeDocument + " : " + contenu);
    }

    // Getters
    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getDatePartage() {
        return datePartage;
    }

    // Setters (facultatifs)
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    // Affichage simple
    @Override
    public String toString() {
        return "[" + typeDocument + "] " + contenu + " (partagé le " + datePartage + ")";
    }
}
