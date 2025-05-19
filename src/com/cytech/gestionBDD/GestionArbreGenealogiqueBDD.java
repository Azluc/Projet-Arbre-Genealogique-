package com.cytech.gestionBDD;

 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
 

public class GestionArbreGenealogiqueBDD{
	private final static String utilisateur = "user";
	private final static String motDePasse = "Password123!";
	 
	public static int ajoutRacineArbre(int idArbre, int codePublic,String prenom) {
        try {
            String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
 

            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);

            String sql = "INSERT INTO Arbre (id_arbre, codePublic, prenom) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idArbre);
            stmt.setInt(2, idArbre);
            stmt.setString(3, prenom);
            

            stmt.executeUpdate();

            stmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
	
	
}

 
