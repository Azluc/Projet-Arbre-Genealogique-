package com.cytech.gestionBDD;
import java.sql.*;

 
import java.util.ArrayList;
 
import java.util.List;

/**
 * Class managing database operations for users.
 */
public class GestionUtilisateurBDD {
	static Connection cnx;
	static Statement st;
	static ResultSet rst;
	private static final String user="user";
	private static final String password="Password123!";
	 
	/**
	 * Establishes a connection to the database.
	 * 
	 * @return The database connection, or null if connection fails
	 */
	 public static Connection connecterDB() {
	    	try {
	    		Class.forName("com.mysql.cj.jdbc.Driver");
	    		//System.out.println("ok");
	    		String url="jdbc:mysql://localhost:3306/arbre_genealogique";
	    		 
	    		Connection cnx=DriverManager.getConnection(url, user, password);
	    	//	System.out.println("Connexion etablie");
	    		return cnx;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	 }

	/**
	 * Adds a new user to the database.
	 * 
	 * @param nom Last name
	 * @param prenom First name
	 * @param dateNaissance Birth date
	 * @param nationalite Nationality
	 * @param numeroSecuriteSociale Social security number
	 * @param email Email address
	 * @param motDePasse Password
	 * @param codePublic Public code
	 * @param codePrive Private code
	 * @param adresse Address
	 * @param telephone Phone number
	 * @param photo Profile photo
	 * @param carte_identite ID card
	 */
	 public static void AjouterUtilisateur(String nom, String prenom, String dateNaissance,
		        String nationalite, String numeroSecuriteSociale, String email,
		        String motDePasse, int codePublic, int codePrive,
		        String adresse, String telephone, byte[] photo, byte[] carte_identite) {

		    String sql = "INSERT INTO utilisateur (nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, mot_de_passe, code_public, code_prive, adresse, telephone, photo, carte_identite) " +
		                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		    try (Connection cnx = connecterDB();
		         PreparedStatement ps = cnx.prepareStatement(sql)) {

		        ps.setString(1, nom);
		        ps.setString(2, prenom);
		        ps.setString(3, dateNaissance);
		        ps.setString(4, nationalite);
		        ps.setString(5, numeroSecuriteSociale);
		        ps.setString(6, email);
		        ps.setString(7, motDePasse);
		        ps.setInt(8, codePublic);
		        ps.setInt(9, codePrive);
		        ps.setString(10, adresse);
		        ps.setString(11, telephone);

		        if (photo != null) {
		            ps.setBytes(12, photo);
		        } else {
		            ps.setNull(12, java.sql.Types.BLOB);
		        }

		        if (carte_identite != null) {
		            ps.setBytes(13, carte_identite);
		        } else {
		            ps.setNull(13, java.sql.Types.BLOB);
		        }

		        ps.executeUpdate();
		      //  System.out.println("Utilisateur ajouté");

		    } catch (Exception e) {
		        System.out.println("Erreur lors de l'insertion : " + e.getMessage());
		        e.printStackTrace();
		    }
		}

	/**
	 * Retrieves a user's first name and password using their private code.
	 * 
	 * @param codePrive The private code
	 * @return A list containing the first name, password, and private code
	 */
	 public static List<Object> getPrenomEtMotDePasseParCodePrive(int codePrive) {
		    List<Object> infos = new ArrayList<>();
		    Connection connexion = null;
		    PreparedStatement statement = null;
		    ResultSet resultat = null;

		    try {
		        connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/arbre_genealogique", "user", "Password123!");
		        String sql = "SELECT prenom, mot_de_passe, code_prive FROM utilisateur WHERE code_prive = ?";
		        statement = connexion.prepareStatement(sql);
		        statement.setInt(1, codePrive); // ici c'est bien setString

		        resultat = statement.executeQuery();

		        if (resultat.next()) {
		            infos.add(resultat.getString("prenom"));
		            infos.add(resultat.getString("mot_de_passe"));
		            infos.add(resultat.getString("code_prive"));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (resultat != null) resultat.close();
		            if (statement != null) statement.close();
		            if (connexion != null) connexion.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }

		    return infos;
		}
	 
	/**
	 * Updates a user's password using their private code.
	 * 
	 * @param codePrive The private code
	 * @param nouveauMotDePasse The new password
	 * @return true if the update was successful, false otherwise
	 */
	 public static boolean modifierMotDePasseParCodePrive(int codePrive, String nouveauMotDePasse) {
		    String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
		    String sql = "UPDATE utilisateur SET mot_de_passe = ? WHERE code_prive = ?";

		    try (Connection conn = DriverManager.getConnection(url, user, password);
		         PreparedStatement requete = conn.prepareStatement(sql)) {

		        requete.setString(1, nouveauMotDePasse);
		        requete.setInt(2, codePrive);

		        int lignesModifiees = requete.executeUpdate();
		        return lignesModifiees > 0; // true si au moins une ligne a été modifiée

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 
	/**
	 * Updates user information using their private code.
	 * 
	 * @param codePrive The private code
	 * @param champEmail New email address
	 * @param champNationalite New nationality
	 * @param champAdresse New address
	 * @param champTelephone New phone number
	 * @return 1 if update successful, -1 if user not found or error occurs, 0 if no fields to update
	 */
	 public static int mettreAJourUtilisateurParCodePrive(int codePrive, String champEmail, String champNationalite, String champAdresse, String champTelephone) {
		 String url="jdbc:mysql://localhost:3306/arbre_genealogique";   
		 try (Connection connexion = DriverManager.getConnection(url, user, password)) {

		        // Construction dynamique de la requête SQL
		        StringBuilder requete = new StringBuilder("UPDATE utilisateur SET ");
		        List<String> champsAChanger = new ArrayList<>();
		        List<Object> valeurs = new ArrayList<>();

		        if (!champEmail.trim().isEmpty()) {
		            champsAChanger.add("email = ?");
		            valeurs.add(champEmail.trim());
		        }
		        if (!champNationalite.trim().isEmpty()) {
		            champsAChanger.add("nationalite = ?");
		            valeurs.add(champNationalite.trim());
		        }
		        if (!champAdresse.trim().isEmpty()) {
		            champsAChanger.add("adresse = ?");
		            valeurs.add(champAdresse.trim());
		        }
		        if (!champTelephone.trim().isEmpty()) {
		            champsAChanger.add("telephone = ?");
		            valeurs.add(champTelephone.trim());
		        }

		        if (champsAChanger.isEmpty()) {
		        	// pas de champs a changer
		           // System.out.println("Aucun champ à modifier.");
		            return 0;
		        }

		        requete.append(String.join(", ", champsAChanger));
		        requete.append(" WHERE code_prive = ?");
		        valeurs.add(codePrive); // dernier paramètre

		        try (PreparedStatement statement = connexion.prepareStatement(requete.toString())) {
		            for (int i = 0; i < valeurs.size(); i++) {
		                statement.setObject(i + 1, valeurs.get(i));
		            }

		            int lignesModifiees = statement.executeUpdate();
		            if (lignesModifiees > 0) {
		            	//Maj des utilisateurs avec succes 
		              //  System.out.println("Utilisateur mis à jour avec succès.");
		                return 1;
		            } else {
		            	//Echec de la maj, on a pas trouvé 'utilitateur
		                //System.out.println("Aucun utilisateur trouvé avec ce code privé.");
		                return -1;
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return -1;
		}

	/**
	 * Searches for a user by their name and first name.
	 * 
	 * @param nom Last name
	 * @param prenom First name
	 */
	 	public static void rechercheparNP(String nom,String prenom) {
	 		try{
	 		
	 			String query = "SELECT * FROM utilisateur WHERE nom='" + nom + "' AND prenom='" + prenom + "'";

	 		
	 		cnx=connecterDB();
 			st=cnx.createStatement();
 			rst=st.executeQuery(query);	
 			if (rst.next()) {
 		        System.out.println("Il existe un utilisateur portant le même nom et prénom");
 		    } else {
 		        System.out.println("Utilisateur non existant");
 			}
 			
	 		}catch(Exception e)	{
	 			System.out.println(e.getMessage());
	 		}
	 	}
	 	
	/**
	 * Updates a user's information.
	 * 
	 * @param id User ID
	 * @param n_mdp New password
	 * @param n_email New email
	 * @param n_telephone New phone number
	 * @param n_adresse New address
	 */
	 	public static void MofifierU(int id, String n_mdp, String n_email,String n_telephone,String n_adresse) {
	 		try {
	 			String query = "UPDATE utilisateur SET mot_de_passe='" + n_mdp
	 	             + "', email=" + n_email
	 	             + ", telephone=" + n_telephone
	 	             + ", adresse='" + n_adresse
	 	             + " WHERE id=" + id + ";";
	 			cnx=connecterDB();
	 			st=cnx.createStatement();
	 			st.executeUpdate(query);
	 			//System.out.println("Utilisateur modifié");

	 			
	 			
	 		}catch(Exception e) {
	 			System.out.println(e.getMessage());
	 		}
	 	}

	 
}
	


