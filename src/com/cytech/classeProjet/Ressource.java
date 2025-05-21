package com.cytech.classeProjet;

import java.time.LocalDateTime;

public class Ressource {

    private TypeDocument typeDocument;   // Enum : TEXTE, IMAGE, VIDEO, etc.
    private String contenu;              // Peut être un lien, un texte, etc.
    private LocalDateTime datePartage;   // Date automatique à la création

    // Constructeur
    public Ressource(TypeDocument typeDocument, String contenu) {
        this.typeDocument = typeDocument;
        this.contenu = contenu;
        this.datePartage = LocalDateTime.now(); 
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

    // Setters
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    // toString pour affichage
    @Override
    public String toString() {
        return "[" + typeDocument + "] " + contenu + " (partagé le " + datePartage + ")";
    }
}
