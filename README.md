#  Projet Arbre Généalogique — ING1

##  Présentation

Ce projet a été réalisé dans le cadre du module de développement en Java.  
L'objectif est de réaliser une application permettant de modéliser et de gérer un arbre généalogique dynamique à l’aide de structures orientées objet, avec ajout de relations, visualisation, et gestion des droits d’accès.

##  Équipe projet

- Allouch Evan 
- Belkacem Said
- Chakroun Charf-Eddine
- Hania Haitam
- Mirzica--Vigé Lucas

##  Objectifs

- Représenter un arbre généalogique 
- Associer des liens familiaux (parents, frères, cousins...)
- Gérer différents niveaux de visibilité (public, protégé, privé)
- Permettre la création, modification et consultation d’un arbre
- Ajouter des ressources associées à une personne (photo, texte...)
- Proposer une interface fonctionnelle et une structure claire
- Préparer une application testable et extensible

##  Technologies utilisées

- Langage : **Java**
- Structure : POO, Enum, Collections (`Map`, `List`)
- Test unitaire : via `main()` + `System.out.println()` (pas JUnit)
- Interface graphique (si implémentée) : `Swing` ou `JavaFX`
- Diagrammes : UML (classes, cas d’utilisation)

##  Structure du projet

```
Projet-Arbre-Genealogique/
├── .settings/
│   └── org.eclipse.core.resources.prefs             # Fichier de config Eclipse
├── bin/
│   └── .gitignore                                   # Empêche de versionner les fichiers compilés
├── src/
│   └── com/
│       └── cytech/
│           ├── classeProjet/
│           │   ├── Administrateur.java
│           │   ├── ArbreGenealogique.java
│           │   ├── ArbreGenealogiquePanel.java
│           │   ├── Cote.java
│           │   ├── Genre.java
│           │   ├── Lien.java
│           │   ├── LienParente.java
│           │   ├── Personne.java
│           │   ├── Relation.java
│           │   ├── TypeRelation.java
│           │   └── Utilisateur.java
│           │
│           ├── classeTestsUnitaires/
│           │   ├── TestAdministrateur.java
│           │   ├── TestArbreGenealogique.java
│           │   ├── TestCote.java
│           │   ├── TestGenre.java
│           │   ├── TestGestionUtilisateurBDD.java
│           │   ├── TestLien.java
│           │   ├── TestPersonne.java
│           │   └── TestUtilisateur.java
│           │
│           ├── fenetres/
│           │   ├── AfficherProfilUtilisateur.fxml
│           │   ├── AfficherProfilUtilisateurController.java
│           │   ├── FormulaireAjoutPersonne.fxml
│           │   ├── FormulaireAjoutPersonneController.java
│           │   ├── ModifierProfilUtilisateur.fxml
│           │   ├── ModifierProfilUtilisateurController.java
│           │   ├── PageAccueil.fxml
│           │   ├── PageAccueilController.java
│           │   ├── PageAdministrateur.fxml
│           │   ├── PageAdministrateurController.java
│           │   ├── PageAjoutListeDeroulante.fxml
│           │   ├── PageAjoutListeDeroulanteController.java
│           │   ├── PageChangementMDP.fxml
│           │   ├── PageChangementMDPController.java
│           │   ├── PageConnexionAdministrateur.fxml
│           │   ├── PageConnexionAdministrateurController.java
│           │   ├── PageConnexionUtilisateur.fxml
│           │   ├── PageConnexionUtilisateurController.java
│           │   ├── PageInformationUtilisateur.fxml
│           │   ├── PageInformationUtilisateurController.java
│           │   ├── PageInscription.fxml
│           │   ├── PageInscriptionController.java
│           │   ├── PagePrincipaleUtilisateur.fxml
│           │   ├── PagePrincipaleUtilisateurController.java
│           │   ├── PageRequetes.fxml
│           │   ├── PageRequetesController.java
│           │   └── RacinePrincipale.fxml
│           │
│           ├── gestionBDD/
│           │   ├── GestionArbreGenealogiqueBDD.java
│           │   ├── GestionDemandeAdhesionBdd.java
│           │   ├── GestionLienParenteBDD.java
│           │   ├── GestionPersonneBDD.java
│           │   └── GestionUtilisateurBDD.java
│           │
│           └── Main.java
│
├── .classpath                                      # Fichier Eclipse
├── .project                                        # Fichier Eclipse
├── README.md                                       # Présentation du projet
├── arbre_genealogique.sql                          # Structure de la base de données
└── diagrammeArbreV2.mdj                            # Modèle UML (StarUML / Modelio)

```

##  Instructions de lancement

### 1. Clonage du dépôt

```bash
git clone https://github.com/Azluc/Projet-Arbre-Genealogique-.git
cd projet-arbre-genealogique
```

### 2. Compilation

```bash
javac -d bin src/com/cytech/classeProjet/*.java src/classeTestsUnitaires/*.java
```

### 3. Lancement d’un test

```bash
java -cp bin com.cytech.classeProjet.TestArbreGenealogique_Console
```

### 4. Lancement de l’application principale (si `Main.java` présent)

```bash
java -cp bin com.cytech.classeProjet.Main
```

##  Diagrammes UML

-  Diagramme de classes : fourni dans le fichier "UML.pdf"


##  Tests

Tous les tests sont réalisés en **ligne de commande** à l’aide de classes `TestXYZ_Console.java`  
Chaque test affiche "OK" ou "ÉCHEC" ou les erreurs associées selon les résultats.

##  Documentation

La documentation Javadoc est générable via eclipse:

Résultat accessible dans le dossier `/doc/' dont la page racine est index.html`

##  Problèmes rencontrés

- Gestion des relations complexes (oncles, cousins...) : modélisation améliorée avec la classe LienParente
- Suppression de JUnit pour respecter les consignes : refonte des tests en console
- Adaptation finale aux consignes de soutenance
- Interface graphique interactive avec échange de ressources difficile à modéliser: résultat de modélisation qu'en console

##  Fonctionnalités non implémentées (ou partiellement)

- Interface graphique complète 
- Parcours graphique de l’arbre (non interactif pour l’instant)

## Pour la soutenance

-  `git clone`, `git log`, compilation, exécution
-  Test local sans crash
-  Capacité à lire, expliquer, et modifier le code en direct

---

##  Remerciements

Merci à notre enseignant pour son accompagnement, et à l’équipe pour son implication tout au long du projet.
