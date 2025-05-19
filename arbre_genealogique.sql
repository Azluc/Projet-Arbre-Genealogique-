CREATE DATABASE IF NOT EXISTS arbre_genealogique;
USE arbre_genealogique;

-- Table des utilisateurs
CREATE TABLE utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL,
    nationalite VARCHAR(50),
    numero_securite_sociale VARCHAR(20),
    email VARCHAR(100),
    mot_de_passe VARCHAR(100),
    code_public int UNIQUE,
    code_prive int UNIQUE,
    adresse TEXT,
    telephone VARCHAR(20),
    photo LONGBLOB,
    carte_identite LONGBLOB
);

-- Table des arbres
CREATE TABLE IF NOT EXISTS Arbre (
    id_arbre INT UNIQUE,
    codePublic INT UNIQUE,
    prenom VARCHAR(50)
);

-- Table des personnes (noeuds) modifiée
CREATE TABLE IF NOT EXISTS Personne (
    id_arbre INT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    profondeur INT,
    dateNaissance DATE,
    dateDeces DATE DEFAULT NULL,
    nomRelation VARCHAR(50),
    coteLien VARCHAR(20),
    UNIQUE(id_arbre, nom, prenom, profondeur)
);

-- Table des relations de type Parent_enfant
CREATE TABLE IF NOT EXISTS Parent_Enfant (
    id_arbre INT NOT NULL,
    nom_parent VARCHAR(100) NOT NULL,
    prenom_parent VARCHAR(100) NOT NULL,
    nom_enfant VARCHAR(100) NOT NULL,
    prenom_enfant VARCHAR(100) NOT NULL,
    UNIQUE(id_arbre, nom_parent, prenom_parent, nom_enfant, prenom_enfant)
);

CREATE TABLE IF NOT EXISTS UnionConjugale (
    id_arbre INT NOT NULL,
    nom_conjoint1 VARCHAR(100) NOT NULL,
    prenom_conjoint1 VARCHAR(100) NOT NULL,
    nom_conjoint2 VARCHAR(100) NOT NULL,
    prenom_conjoint2 VARCHAR(100) NOT NULL,
    UNIQUE(id_arbre, nom_conjoint1, prenom_conjoint1, nom_conjoint2, prenom_conjoint2)
);

CREATE TABLE IF NOT EXISTS Frere_Soeur (
    id_arbre INT NOT NULL,
    nom1 VARCHAR(100) NOT NULL,
    prenom1 VARCHAR(100) NOT NULL,
    nom2 VARCHAR(100) NOT NULL,
    prenom2 VARCHAR(100) NOT NULL,
    UNIQUE(id_arbre, nom1, prenom1, nom2, prenom2)
);

-- Table des administrateur
CREATE TABLE IF NOT EXISTS administrateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    identifiant VARCHAR(50),
    mot_de_passe VARCHAR(50)
);

-- Table des demandes d’adhésion
CREATE TABLE IF NOT EXISTS demande_adhesion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL,
    nationalite VARCHAR(50),
    numero_securite_sociale VARCHAR(20),
    email VARCHAR(100),
    adresse TEXT,
    telephone VARCHAR(20),
    photo LONGBLOB,
    photoLien TEXT,
    carte_identite LONGBLOB,
    carte_identiteLien TEXT
);

-- Table des souvenirs (partage entre membres liés)
CREATE TABLE souvenir (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_auteur INT NOT NULL,
    type ENUM('PHOTO', 'VIDEO', 'MESSAGE'),
    contenu LONGBLOB,
    FOREIGN KEY (id_auteur) REFERENCES utilisateur(id)
);

-- Table des statistiques de consultation
CREATE TABLE statistiques_consultation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_utilisateur INT,
    id_visiteur INT,
    date_consultation DATETIME,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id),
    FOREIGN KEY (id_visiteur) REFERENCES utilisateur(id)
);

