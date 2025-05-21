package com.cytech.gestionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cytech.classeProjet.Genre;
import com.cytech.classeProjet.Personne;

public class GestionArbreGenealogiqueBDD {
    private static final String UTILISATEUR = "user";
    private static final String MOT_DE_PASSE = "Password123!";
    private static final String URL_BDD = "jdbc:mysql://localhost:3306/arbre_genealogique";

    private static Map<String, Personne> personnes = new HashMap<>();
    private static Connection connexion;

    /**
     * Initialise la connexion statique à la base de données.
     * Doit être appelée une fois au démarrage avant d'utiliser d'autres méthodes.
     */
    public static void initConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection(URL_BDD, UTILISATEUR, MOT_DE_PASSE);
        }
    }

    /**
     * Vérifie que la connexion est initialisée avant usage.
     */
    private static void verifierConnexion() {
        if (connexion == null) {
            throw new IllegalStateException("Connexion non initialisée. Appelez initConnexion() avant.");
        }
    }
    
    /**
     * Charge l'arbre généalogique depuis la BDD et garde une référence unique pour chaque personne
     */
    public static void chargerArbreDepuisBDD(int idArbre) throws SQLException {
        verifierConnexion();

        // Vider complètement la map pour éviter les références orphelines
        personnes.clear();

        System.out.println("Chargement de l'arbre " + idArbre + " depuis la BDD...");

        // Charger les personnes
        String sqlPersonnes = "SELECT * FROM Personne WHERE id_arbre = ?";
        try (PreparedStatement ps = connexion.prepareStatement(sqlPersonnes)) {
            ps.setInt(1, idArbre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    Date dateNaissance = rs.getDate("dateNaissance");
                    Date dateDeces = rs.getDate("dateDeces");
                    Genre genre = Genre.valueOf(rs.getString("genre").toUpperCase());
                    int profondeur = rs.getInt("profondeur");

                    String cle = clePersonne(nom, prenom);
                    Personne personne = new Personne(nom, prenom, dateNaissance, dateDeces, genre, idArbre, profondeur);
                    personnes.put(cle, personne);
                    
                     
                }
            }
        }

        // Charger les relations parent-enfant
        String sqlParents = "SELECT * FROM Parent_Enfant WHERE id_arbre = ?";
        try (PreparedStatement ps = connexion.prepareStatement(sqlParents)) {
            ps.setInt(1, idArbre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nomParent = rs.getString("nom_parent");
                    String prenomParent = rs.getString("prenom_parent");
                    String nomEnfant = rs.getString("nom_enfant");
                    String prenomEnfant = rs.getString("prenom_enfant");

                    Personne parent = personnes.get(clePersonne(nomParent, prenomParent));
                    Personne enfant = personnes.get(clePersonne(nomEnfant, prenomEnfant));

                    if (parent != null && enfant != null) {
                        parent.getEnfants().add(enfant);
                        enfant.getParents().add(parent);
 
                    } else {
                        System.out.println("ERREUR: Relation parent-enfant non établie - parent ou enfant introuvable");
                    }
                }
            }
        }

        // Charger les relations frères/soeurs
        String sqlFreres = "SELECT * FROM Frere_Soeur WHERE id_arbre = ?";
        try (PreparedStatement ps = connexion.prepareStatement(sqlFreres)) {
            ps.setInt(1, idArbre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nom1 = rs.getString("nom1");
                    String prenom1 = rs.getString("prenom1");
                    String nom2 = rs.getString("nom2");
                    String prenom2 = rs.getString("prenom2");

                    Personne p1 = personnes.get(clePersonne(nom1, prenom1));
                    Personne p2 = personnes.get(clePersonne(nom2, prenom2));

                    if (p1 != null && p2 != null) {
                        if (!p1.getFreresEtSoeurs().contains(p2)) {
                            p1.getFreresEtSoeurs().add(p2);
                        }
                        if (!p2.getFreresEtSoeurs().contains(p1)) {
                            p2.getFreresEtSoeurs().add(p1);
                        }
                         
                    } else {
                        System.out.println("ERREUR: Relation frère/soeur non établie - personnes introuvables");
                    }
                }
            }
        }

        // Charger les unions conjugales
        String sqlUnions = "SELECT * FROM UnionConjugale WHERE id_arbre = ?";
        try (PreparedStatement ps = connexion.prepareStatement(sqlUnions)) {
            ps.setInt(1, idArbre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nom1 = rs.getString("nom_conjoint1");
                    String prenom1 = rs.getString("prenom_conjoint1");
                    String nom2 = rs.getString("nom_conjoint2");
                    String prenom2 = rs.getString("prenom_conjoint2");

                    Personne conjoint1 = personnes.get(clePersonne(nom1, prenom1));
                    Personne conjoint2 = personnes.get(clePersonne(nom2, prenom2));

                    if (conjoint1 != null && conjoint2 != null) {
                        conjoint1.setConjoint(conjoint2);
                        conjoint2.setConjoint(conjoint1);
                        System.out.println("Union conjugale: " + prenom1 + " " + nom1 + 
                                           " <=> " + prenom2 + " " + nom2);
                    } else {
                        System.out.println("ERREUR: Union conjugale non établie - conjoints introuvables");
                    }
                }
            }
        }
        
        //System.out.println("Arbre chargé : " + personnes.size() + " personnes");
    }

    /**
     * Génère une clé unique pour identifier une personne dans la map
     */
    private static String clePersonne(String nom, String prenom) {
        return nom.trim().toLowerCase() + ";" + prenom.trim().toLowerCase();
    }

    /**
     * Retourne la collection de toutes les personnes chargées
     */
    public static Collection<Personne> getPersonnes() {
        return personnes.values();
    }

    /**
     * Ajoute une nouvelle racine d'arbre dans la base de données
     */
    public static int ajoutRacineArbre(int idArbre, int codePublic, String prenom) throws SQLException {
        // Ici on crée une nouvelle connexion indépendante pour l'insertion.
        try (Connection conn = DriverManager.getConnection(URL_BDD, UTILISATEUR, MOT_DE_PASSE);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Arbre (id_arbre, codePublic, prenom) VALUES (?, ?, ?)")) {

            stmt.setInt(1, idArbre);
            stmt.setInt(2, codePublic);
            stmt.setString(3, prenom);

            return stmt.executeUpdate();
        } 
    }
    
    /**
     * Récupère le prénom racine de l'arbre pour un codePrivé donné
     */
    public static String getPrenomRacine(int codePrive) throws SQLException {
        verifierConnexion();
        String prenom = null;
        String sql = "SELECT prenom FROM Arbre WHERE id_arbre = ?";
        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setInt(1, codePrive);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prenom = rs.getString("prenom");
                    //System.out.println("Prénom racine trouvé: " + prenom);
                    return prenom;
                }
            }
        }
        System.out.println("ERREUR: Prénom racine non trouvé pour codePrive " + codePrive);
        return null;
    }

    /**
     * Récupère la personne racine dans Personne à partir de id_arbre (codePrive) et prénom
     */
    public static Personne getPersonneRacine(int codePrive, String prenom) throws SQLException {
        verifierConnexion();
        
        // D'abord chercher dans la map des personnes déjà chargées
        for (Personne p : personnes.values()) {
            if (p.getId_arbre() == codePrive && 
                p.getPrenom().equalsIgnoreCase(prenom) && 
                p.getProfondeur() == 0) {
                //System.out.println("Racine trouvée dans la map: " + p.getPrenom() + " " + p.getNom());
                return p;
            }
        }
        
        // Si pas trouvée, chercher dans la BDD
        String sql = "SELECT nom, prenom, dateNaissance, dateDeces, profondeur, genre FROM Personne " +
                     "WHERE id_arbre = ? AND LOWER(prenom) = LOWER(?) AND profondeur = 0";
        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setInt(1, codePrive);
            ps.setString(2, prenom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    Date dateNaissance = rs.getDate("dateNaissance");
                    Date dateDeces = rs.getDate("dateDeces");
                    int profondeur = rs.getInt("profondeur");
                    Genre genre = Genre.valueOf(rs.getString("genre").toUpperCase());

                    Personne racine = new Personne(nom, prenom, dateNaissance, dateDeces, genre, codePrive, profondeur);
                    //System.out.println("Racine trouvée dans la BDD: " + racine.getPrenom() + " " + racine.getNom());
                    
                    // Ajouter à la map si pas encore présente
                    String cle = clePersonne(nom, prenom);
                    if (!personnes.containsKey(cle)) {
                        personnes.put(cle, racine);
                    }
                    
                    return racine;
                }
                else {
                    System.out.println("ERREUR: Racine introuvable pour codePrive " + codePrive + 
                                      " et prénom " + prenom);
                }
            }
        }
        return null;
    }
    
    /**
     * Ajoute une nouvelle personne à l'arbre généalogique
     */
    public static boolean ajouterPersonne(Personne personne) throws SQLException {
        verifierConnexion();
        
        String sql = "INSERT INTO Personne (nom, prenom, dateNaissance, dateDeces, genre, id_arbre, profondeur) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
                     
        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, personne.getNom());
            ps.setString(2, personne.getPrenom());
            ps.setDate(3, personne.getDateNaissance() != null ? 
                        new java.sql.Date(personne.getDateNaissance().getTime()) : null);
            ps.setDate(4, personne.getDateDeces() != null ? 
                        new java.sql.Date(personne.getDateDeces().getTime()) : null);
            ps.setString(5, personne.getGenre().toString());
            ps.setInt(6, personne.getId_arbre());
            ps.setInt(7, personne.getProfondeur());
            
            int result = ps.executeUpdate();
            
            // Ajouter la personne à la map locale
            if (result > 0) {
                String cle = clePersonne(personne.getNom(), personne.getPrenom());
                personnes.put(cle, personne);
                System.out.println("Personne ajoutée avec succès: " + personne.getPrenom() + " " + personne.getNom());
                return true;
            }
            
            return false;
        }
    }
    
    /**
     * Ajoute une relation parent-enfant dans la base de données
     */
    public static boolean ajouterRelationParentEnfant(Personne parent, Personne enfant) throws SQLException {
        verifierConnexion();
        
        String sql = "INSERT INTO Parent_Enfant (nom_parent, prenom_parent, nom_enfant, prenom_enfant, id_arbre) " +
                     "VALUES (?, ?, ?, ?, ?)";
                     
        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, parent.getNom());
            ps.setString(2, parent.getPrenom());
            ps.setString(3, enfant.getNom());
            ps.setString(4, enfant.getPrenom());
            ps.setInt(5, parent.getId_arbre()); // Supposons que parent et enfant appartiennent au même arbre
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                // Mettre à jour les relations en mémoire
                parent.getEnfants().add(enfant);
                enfant.getParents().add(parent);
                
                return true;
            }
            
            return false;
        }
    }
    
    /**
     * Ajoute une relation frère/soeur dans la base de données
     */
    public static boolean ajouterRelationFrereSoeur(Personne p1, Personne p2) throws SQLException {
        verifierConnexion();
        
        String sql = "INSERT INTO Frere_Soeur (nom1, prenom1, nom2, prenom2, id_arbre) " +
                     "VALUES (?, ?, ?, ?, ?)";
                     
        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, p1.getNom());
            ps.setString(2, p1.getPrenom());
            ps.setString(3, p2.getNom());
            ps.setString(4, p2.getPrenom());
            ps.setInt(5, p1.getId_arbre());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                // Mettre à jour les relations en mémoire
                if (!p1.getFreresEtSoeurs().contains(p2)) {
                    p1.getFreresEtSoeurs().add(p2);
                }
                if (!p2.getFreresEtSoeurs().contains(p1)) {
                    p2.getFreresEtSoeurs().add(p1);
                }
                 
                return true;
            }
            
            return false;
        }
    }
    
    /**
     * Ajoute une union conjugale dans la base de données
     */
    public static boolean ajouterUnionConjugale(Personne conjoint1, Personne conjoint2) throws SQLException {
        verifierConnexion();
        
        String sql = "INSERT INTO UnionConjugale (nom_conjoint1, prenom_conjoint1, nom_conjoint2, prenom_conjoint2, id_arbre) " +
                     "VALUES (?, ?, ?, ?, ?)";
                     
        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, conjoint1.getNom());
            ps.setString(2, conjoint1.getPrenom());
            ps.setString(3, conjoint2.getNom());
            ps.setString(4, conjoint2.getPrenom());
            ps.setInt(5, conjoint1.getId_arbre());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                // Mettre à jour les relations en mémoire
                conjoint1.setConjoint(conjoint2);
                conjoint2.setConjoint(conjoint1);
                 
                return true;
            }
            
            return false;
        }
    }
}