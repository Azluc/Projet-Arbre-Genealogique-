#  Projet Arbre Généalogique — ING1

##  Présentation

Ce projet a été réalisé dans le cadre du module de développement en Java.  
Il s'agit d'une application permettant de modéliser et de gérer un arbre généalogique dynamique à l’aide de structures orientées objet, avec ajout de relations, visualisation, et gestion des droits d’accès.

##  Équipe projet

- Allouch Evan 
- Belkacem Said
- Chakroun Charf-Eddine
- Hania Haitam
- Mirzica--Vigé Lucas

##  Objectifs

- Représenter un arbre généalogique sous forme d’un graphe orienté
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
src/
├── com/cytech/classeProjet/
│   ├── ArbreGenealogique.java
│   ├── Personne.java
│   ├── LienParente.java
│   ├── NiveauVisibilite.java
│   ├── Noeud.java
│   ├── Ressource.java
│   ├── ...
├── classeTestsUnitaires/
│   ├── TestPersonne_Console.java
│   ├── TestAdministrateur_Console.java
│   └── ...
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

-  Diagramme de classes : fourni dans le dossier `/docs`
-  Diagramme de cas d’utilisation : fourni dans le dossier `/docs`

##  Tests

Tous les tests sont réalisés en **ligne de commande** à l’aide de classes `TestXYZ_Console.java`  
Chaque test affiche "OK" ou "ÉCHEC" selon les résultats.

##  Documentation

La documentation Javadoc est générable via :

```bash
javadoc -d doc/ src/com/cytech/classeProjet/*.java
```

Résultat accessible dans le dossier `/doc/index.html`

##  Problèmes rencontrés

- Gestion des relations complexes (oncles, cousins...) : modélisation améliorée avec la classe `Noeud`
- Suppression de JUnit pour respecter les consignes : refonte des tests en console
- Adaptation finale aux consignes de soutenance

##  Fonctionnalités non implémentées (ou partiellement)

- Interface graphique complète (si absente)
- Persistance en base de données (système uniquement en mémoire)
- Parcours graphique de l’arbre (non interactif pour l’instant)

## Pour la soutenance

-  `git clone`, `git log`, compilation, exécution
-  Test local sans crash
-  Capacité à lire, expliquer, et modifier le code en direct

---

##  Remerciements

Merci à notre enseignant pour son accompagnement, et à l’équipe pour son implication tout au long du projet.
