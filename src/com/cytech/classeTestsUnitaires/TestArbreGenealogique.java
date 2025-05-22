package com.cytech.classeTestsUnitaires;

import java.text.ParseException;


import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;

import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.ArbreGenealogiquePanel;
import com.cytech.classeProjet.Cote;
import com.cytech.classeProjet.Genre;
 
import com.cytech.classeProjet.LienParente;
import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;

public class TestArbreGenealogique {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws ParseException {
        System.out.println("Création de la personne racine :");
        Personne racine = creerPersonne();
        ArbreGenealogique arbre = new ArbreGenealogique(racine, 1, new HashSet<>());
        arbre.getPersonnes().add(racine);
    
        LienParente liensParente = new LienParente(arbre);
        arbre.setLiensParente(liensParente);
        while (true) {
            ArbreGenealogique.afficherRelations(arbre);
            JFrame frame = new JFrame("Arbre généalogique");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ArbreGenealogiquePanel(arbre));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println("\nSouhaitez-vous ajouter une nouvelle personne ? (oui/non)");
            if (!scanner.nextLine().equalsIgnoreCase("oui")) break;

            System.out.println("Choisissez une personne de référence dans l'arbre :");
            Personne reference = choisirPersonne(arbre.getPersonnes());

            if (!arbre.getPersonnes().contains(reference)) {
                System.out.println("Erreur : la personne de référence n'existe pas dans l'arbre.");
                continue;
            }

            if (arbre.getRacine().getConjoint() != null && reference.equals(arbre.getRacine().getConjoint())) {
                System.out.println("Ajout interdit : la racine est mariée, vous ne pouvez pas ajouter des membres de la belle-famille.");
                continue;
            }

            System.out.println("Quel est le lien avec cette personne ? (1: parent, 2: enfant, 3: frère/soeur)");
            int choix = Integer.parseInt(scanner.nextLine());

            Personne nouvelle = creerPersonne();

            boolean existeDeja = arbre.getPersonnes().stream()
                .anyMatch(p -> p.estIdentique(nouvelle));

            if (existeDeja) {
                System.out.println("Erreur : cette personne existe déjà dans l'arbre. Opération annulée.");
                continue;
            }

            boolean ajoutReussi = false;
            TypeRelation typeRelation = null;

            switch (choix) {
                case 1: // parent
                    if (Personne.aAuMoins18AnsDePlus(nouvelle.getDateNaissance(), reference.getDateNaissance())) {
                        nouvelle.setProfondeur(reference.getProfondeur() + 1);
                        nouvelle.ajouterEnfant(reference,arbre);
                        reference.ajouterParent(nouvelle);
                        ajoutReussi = true;
                        typeRelation = TypeRelation.PARENT_ENFANT;
                        
                    } else {
                        System.out.println("Erreur : Le parent doit être plus âgé que l’enfant.");
                    }
                    break;
                case 2: // enfant
                    if (Personne.aAuMoins18AnsDePlus(reference.getDateNaissance(), nouvelle.getDateNaissance())) {
                        nouvelle.setProfondeur(reference.getProfondeur() - 1);
                        reference.ajouterEnfant(nouvelle,arbre);
                        nouvelle.ajouterParent(reference);
                        nouvelle.setCote(Cote.NEUTRE);
                        ajoutReussi = true;
                        typeRelation = TypeRelation.PARENT_ENFANT;
                    } else {
                        System.out.println("Erreur : L’enfant doit être plus jeune que le parent.");
                    }
                    break;
                case 3: // frère/soeur
                    nouvelle.setProfondeur(reference.getProfondeur());
                    reference.ajouterFrereOuSoeur(nouvelle,arbre);
                    nouvelle.setCote(Cote.NEUTRE);
                    ajoutReussi = true;
                    typeRelation = TypeRelation.FRERE_SOEUR;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

            if (ajoutReussi) {
                arbre.getPersonnes().add(nouvelle);

             // Obtenir le nom de la relation
                String nomRelation = ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, nouvelle);
                 
                // Ajouter le lien via l'objet unique LienParente lié à l'arbre
                if (typeRelation == TypeRelation.PARENT_ENFANT) {
                    // Assurer que la personne source est le parent et destination est l'enfant
                    if (reference.getDateNaissance().after(nouvelle.getDateNaissance())) {
                        // reference est plus jeune que nouvelle, donc on inverse
                        arbre.getLiensParente().ajouterLien(nouvelle, reference, typeRelation, nomRelation);
                    } else {
                        arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
                    }
                } else {
                    // Autres types (FRERE_SOEUR, UNION) : on garde l’ordre normal
                    arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
                }

                System.out.println(nouvelle.getNomComplet() + " a une profondeur de " + nouvelle.getProfondeur());
            } else {
                System.out.println("Ajout annulé (âge incohérent ou autre erreur).");
            }

            // Met à jour les relations automatiques (frères/soeurs par exemple)
            for (Personne p : arbre.getPersonnes()) {
                p.detecterFreresEtSoeurs(arbre.getPersonnes(),arbre);
            }
        }

        System.out.println("Fin de la construction de l'arbre.");
        // Afficher lien parente de l'arbre
        
        System.out.println("\nListe des liens de parenté dans l'arbre :");
        ArbreGenealogique.afficherRelations(arbre);
        
        
        
        
        
    }
 
    private static Personne creerPersonne() throws ParseException {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Date de naissance (dd/MM/yyyy) : ");
        Date naissance = dateFormat.parse(scanner.nextLine());
        System.out.print("Genre (HOMME/FEMME) : ");
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());

        return new Personne(nom, prenom, naissance, null, genre, 1, 0);
    }
    
    private static Personne choisirPersonne(Set<Personne> personnes) {
        int i = 1;
        List<Personne> liste = new ArrayList<>(personnes);
        for (Personne p : liste) {
            System.out.println(i + ": " + p.getNomComplet());
            i++;
        }
        int choix = Integer.parseInt(scanner.nextLine());
        return liste.get(choix - 1);
    }
    
 
} 