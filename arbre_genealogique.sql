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
    code_public VARCHAR(100) UNIQUE,
    code_prive VARCHAR(100) UNIQUE,
    adresse TEXT,
    telephone VARCHAR(20),
    photo varchar(200),
    carte_identite varchar(200)
);

-- Table des arbres
CREATE TABLE arbre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_utilisateur INT NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id)
);

-- Table des personnes (noeuds)
CREATE TABLE personne (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    date_naissance DATE,
    genre VARCHAR(10),
    adresse TEXT,
    email VARCHAR(100),
    telephone VARCHAR(20),
    est_inscrit BOOLEAN DEFAULT FALSE
);

-- Table des noeuds dans l’arbre
CREATE TABLE noeud_personne (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_arbre INT NOT NULL,
    id_personne INT NOT NULL,
    lien_parente VARCHAR(50),
    visibilite ENUM('PUBLIC', 'PRIVATE', 'PROTECTED'),
    FOREIGN KEY (id_arbre) REFERENCES arbre(id),
    FOREIGN KEY (id_personne) REFERENCES personne(id)
);

-- Table des liens parentaux entre noeuds (relations bidirectionnelles possibles)
CREATE TABLE relation_parente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_source INT NOT NULL,
    id_cible INT NOT NULL,
    type_relation VARCHAR(50),
    FOREIGN KEY (id_source) REFERENCES noeud_personne(id),
    FOREIGN KEY (id_cible) REFERENCES noeud_personne(id)
);

-- Table des demandes d’adhésion
CREATE TABLE demande_adhesion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    date_naissance DATE,
    nationalite VARCHAR(50),
    numero_securite_sociale VARCHAR(20),
    email VARCHAR(100),
    photo LONGBLOB,
    carte_identite LONGBLOB,
    statut ENUM('EN_ATTENTE', 'ACCEPTEE', 'REFUSEE')
);

-- Table des souvenirs (partage entre membres liés)
CREATE TABLE souvenir (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_auteur INT NOT NULL,
    type ENUM('PHOTO', 'VIDEO', 'MESSAGE'),
    contenu LONGBLOB,
    FOREIGN KEY (id_auteur) REFERENCES utilisateur(id)
);

-- Table de partage des souvenirs
CREATE TABLE partage_souvenir (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_souvenir INT,
    id_destinataire INT,
    FOREIGN KEY (id_souvenir) REFERENCES souvenir(id),
    FOREIGN KEY (id_destinataire) REFERENCES utilisateur(id)
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





