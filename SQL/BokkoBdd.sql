DROP database IF EXISTS BokkoDB;
CREATE DATABASE BokkoDB;
USE BokkoDB;
DROP TABLE IF EXISTS Utilisateur;
CREATE TABLE Utilisateur (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(255) not null,
                        prenom VARCHAR(255) not null,
                        email VARCHAR(254) UNIQUE not null,
                        motDePasse VARCHAR(255) not null,
                        telephone VARCHAR(20) not null,
                        dateInscription datetime not null,
                        statut enum('A', 'I', 'B') not null
);
drop table if exists Profil;
CREATE TABLE Profil (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        utilisateur_idUtilisateur INT,
                        bio TEXT,
                        photo VARCHAR(255),
                        FOREIGN KEY (utilisateur_idUtilisateur) REFERENCES Utilisateur(id)
);
drop table if exists Trajet;
CREATE TABLE Trajet (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        conducteur_idUtilisateur INT,
                        depart VARCHAR(255) not null,
                        arrivee VARCHAR(255) not null,
                        dateHeureDepart DATETIME not null,
                        nombrePlaces INT not null,
                        prix FLOAT not null,
                        statut ENUM('à venir', 'en cours', 'terminé', 'annulée'),
                        FOREIGN KEY (conducteur_idUtilisateur) REFERENCES Utilisateur(id)
);
drop table if exists Reservation;
CREATE TABLE Reservation (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        trajet_idTrajet INT,
                        passager_idUtilisateur INT,
                        nombrePlacesReservees INT,
                        statut ENUM('en attente', 'confirmé', 'fini'),
                        FOREIGN KEY (trajet_idTrajet) REFERENCES Trajet(id),
                        FOREIGN KEY (passager_idUtilisateur) REFERENCES Utilisateur(id)
);
drop table if exists Vehicule;
CREATE TABLE Vehicule (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        conducteur_idUtilisateur INT,
                        marque VARCHAR(255),
                        modele VARCHAR(255),
                        couleur VARCHAR(50),
                        immatriculation VARCHAR(50),
                        annee INT,
                        FOREIGN KEY (conducteur_idUtilisateur) REFERENCES Utilisateur(id)
);
drop table if exists Paiement;
CREATE TABLE Paiement (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        reservation_idReservation INT not null,
                        montant FLOAT,
                        dateHeure DATETIME,
                        methode ENUM('CB', 'OrangeBank'),
                        statut ENUM('en attente', 'confirmée', 'annulée'),
                        FOREIGN KEY (reservation_idReservation) REFERENCES Reservation(id)
);
drop table if exists Avis;
CREATE TABLE Avis (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        reservation_idReservation INT not null,
                        utilisateur_idUtilisateur int not null,
                        note INT not null,
                        commentaire TEXT,
                        dateHeure DATETIME,
                        FOREIGN KEY (reservation_idReservation) REFERENCES Reservation(id)
);
drop table if exists Message;
CREATE TABLE Message (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         expediteur_idUtilisateur INT,
                         destinataire_idUtilisateur INT,
                         contenu TEXT,
                         dateHeureEnvoi DATETIME,
                         lu BOOLEAN,
                         FOREIGN KEY (expediteur_idUtilisateur) REFERENCES Utilisateur(id),
                         FOREIGN KEY (destinataire_idUtilisateur) REFERENCES Utilisateur(id)
);

alter table Utilisateur
    ADD INDEX idx_adresseEmail (email);

drop table if exists session;
create table session(
                        id int not null auto_increment primary key,
                        user_email char(254) not null,
                        token char(254) not null,
                        dateDebutSession datetime not null,
                        dateFinSession datetime,
                        actif char(1) not null,
                        foreign key (user_email) references Utilisateur(email)
);

