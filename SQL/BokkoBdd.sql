DROP database IF EXISTS BokkoDB;
CREATE DATABASE BokkoDB;
USE BokkoDB;
DROP TABLE IF EXISTS utilisateur;
CREATE TABLE utilisateur (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(255) not null,
                        prenom VARCHAR(255) not null,
                        email VARCHAR(254) UNIQUE not null,
                        mot_de_passe VARCHAR(255) not null,
                        telephone VARCHAR(20) not null,
                        date_inscription datetime not null,
                        statut enum('A', 'I', 'B') not null
);
drop table if exists profil;
CREATE TABLE profil (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        utilisateur_id_utilisateur INT,
                        bio TEXT,
                        photo LONGTEXT,
                        FOREIGN KEY (utilisateur_id_utilisateur) REFERENCES utilisateur(id)
);
drop table if exists Trajet;
CREATE TABLE trajet (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        conducteur_id_utilisateur INT,
                        depart VARCHAR(255) not null,
                        arrivee VARCHAR(255) not null,
                        date_heure_depart DATETIME not null,
                        nombre_places INT not null,
                        prix FLOAT not null,
                        statut ENUM('à venir', 'en cours', 'terminé', 'annulée'),
                        FOREIGN KEY (conducteur_id_utilisateur) REFERENCES utilisateur(id)
);
drop table if exists Reservation;
CREATE TABLE reservation (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        trajet_id_trajet INT,
                        passager_id_utilisateur INT,
                        nombre_places_reservees INT,
                        date_reservation datetime not null,
                        statut ENUM('en attente', 'confirmé', 'fini'),
                        FOREIGN KEY (trajet_id_trajet) REFERENCES trajet(id),
                        FOREIGN KEY (passager_id_utilisateur) REFERENCES utilisateur(id)
);
drop table if exists Vehicule;
CREATE TABLE vehicule (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        conducteur_id_utilisateur INT,
                        marque VARCHAR(255),
                        modele VARCHAR(255),
                        couleur VARCHAR(50),
                        immatriculation VARCHAR(50),
                        annee INT,
                        used tinyint not null,
                        FOREIGN KEY (conducteur_id_utilisateur) REFERENCES utilisateur(id)
);
drop table if exists Paiement;
CREATE TABLE paiement (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        reservation_id_reservation INT not null,
                        utilisateur_id_utilisateur int not null,
                        montant FLOAT,
                        date_heure DATETIME,
                        methode ENUM('CB', 'OrangeBank'),
                        statut ENUM('en attente', 'confirmée', 'annulée'),
                        FOREIGN KEY (reservation_id_reservation) REFERENCES reservation(id),
                        foreign key (utilisateur_id_utilisateur) references utilisateur(id)
);
drop table if exists Avis;
CREATE TABLE avis (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        reservation_id_reservation INT not null,
                        utilisateur_id_utilisateur int not null,
                        note INT not null,
                        commentaire TEXT,
                        date_heure DATETIME,
                        FOREIGN KEY (reservation_id_reservation) REFERENCES reservation(id),
                        FOREIGN KEY (utilisateur_id_utilisateur) references utilisateur(id)
);
drop table if exists Message;
CREATE TABLE message (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         expediteur_id_utilisateur INT,
                         destinataire_id_utilisateur INT,
                         contenu TEXT,
                         date_heure_envoi DATETIME,
                         lu BOOLEAN,
                         FOREIGN KEY (expediteur_id_utilisateur) REFERENCES utilisateur(id),
                         FOREIGN KEY (destinataire_id_utilisateur) REFERENCES utilisateur(id)
);

alter table utilisateur
    ADD INDEX idx_adresseEmail (email);

drop table if exists session;
create table session(
                        id int not null auto_increment primary key,
                        user_email char(254) not null,
                        token char(254) not null,
                        date_debut_session datetime not null,
                        date_fin_session datetime,
                        actif char(1) not null,
                        foreign key (user_email) references utilisateur(email)
);

ALTER TABLE vehicule MODIFY used tinyint NOT NULL DEFAULT 1;

UPDATE utilisateur
SET
    nom = COALESCE(NULLIF(nom, ''), 'Si Yakoub'),
    prenom = COALESCE(NULLIF(prenom, ''), 'Mourad'),
    email = COALESCE(NULLIF(email, ''), 'siyakoubm@gmail.com'),
    telephone = COALESCE(NULLIF(telephone, ''), '0681412651')
WHERE
    nom = '' OR prenom = '' OR email = '' OR telephone = '';

ALTER TABLE reservation MODIFY COLUMN statut ENUM('en attente', 'confirmé', 'fini', 'Annulé');

ALTER TABLE reservation ADD UNIQUE INDEX UK_passager_trajet (passager_id_utilisateur, trajet_id_trajet);