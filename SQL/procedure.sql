use BokkoDB;
DELIMITER //

DROP PROCEDURE IF EXISTS sp_createTrajet;

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_createTrajet`(
    IN p_conducteur_user_id INT,
    IN p_depart VARCHAR(255),
    IN p_arrivee VARCHAR(255),
    IN p_dateHeureDepart DATETIME,
    IN p_nombresPlaces INT,
    IN p_price FLOAT,
    IN p_statut ENUM('à venir', 'en cours', 'terminé', 'annulée')
)
BEGIN
    -- Vérifier si un trajet existe déjà à la même date pour ce conducteur avec un statut différent de 'annulée'
    IF EXISTS (
        SELECT 1
        FROM trajet
        WHERE conducteur_id_utilisateur = p_conducteur_user_id
          AND date_heure_depart = p_dateHeureDepart
          AND statut != 'annulée'
    )
    THEN
        -- Lever une exception si un trajet est trouvé
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Un trajet est déjà enregistré à cette date pour ce conducteur';
    ELSE
        -- Insérer le nouveau trajet si aucun conflit n'est détecté
        INSERT INTO trajet (conducteur_id_utilisateur, depart, arrivee, date_heure_depart, nombre_places, prix, statut)
        VALUES (p_conducteur_user_id, p_depart, p_arrivee, p_dateHeureDepart, p_nombresPlaces, p_price, p_statut);
    END IF;
END;
//
DELIMITER ;

DELIMITER //

DROP PROCEDURE IF EXISTS sp_createVehicule;

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_createVehicule`(
    in p_conducteur_user_id int,
    in p_marque varchar(255),
    in p_model varchar(255),
    in p_couleur varchar(50),
    in p_immatriculation varchar(50),
    in p_annee int,
    in p_use tinyint
)
begin
    IF EXISTS (
        SELECT 1
        FROM vehicule
        WHERE conducteur_id_utilisateur = p_conducteur_user_id
          AND immatriculation = p_immatriculation
    )
    then
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Un trajet est déjà enregistré à cette date pour ce conducteur';
    else
        insert into vehicule(conducteur_id_utilisateur, marque, modele, couleur, immatriculation, annee, used)
            values (p_conducteur_user_id, p_marque, p_model, p_couleur, p_immatriculation, p_annee, p_use);
    end if;
end; //
delimiter ;

delimiter //

drop procedure if exists sp_createProfil;
create definer=`root`@`localhost` procedure `sp_createProfil`(
    in p_name varchar(255),
    in p_prenom varchar(255),
    in p_email varchar(254),
    in p_motDePasse varchar(255),
    in p_telephone varchar(20),
    in p_dateInscription datetime,
    in p_statut enum('A', 'I', 'B'),
    in p_biographie text,
    in p_photo LONGTEXT
)
begin
    declare new_user_id int;

    set new_user_id = createUser(p_name, p_prenom, p_email, p_motDePasse, p_telephone, p_dateInscription, p_statut);

    if new_user_id=-1 then
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Adresse e-mail déjà utilisé...';
    else
        insert into profil(utilisateur_id_utilisateur, bio, photo) values (new_user_id, p_biographie, p_photo);
        SELECT 'OK' as status;
    end if;

end; //
delimiter ;


delimiter //
drop procedure if exists sp_createMessage;
create definer=`root`@`localhost` procedure `sp_createMessage`(
    in p_expediteur_id int,
    in p_destinataire_id int,
    in p_contenu text,
    in p_dateHeureEnvoi datetime,
    in p_vu boolean
)
begin
    insert into message(expediteur_id_utilisateur, destinataire_id_utilisateur, contenu, date_heure_envoi, lu) values (p_expediteur_id, p_destinataire_id, p_contenu, p_dateHeureEnvoi, p_vu);
end; //
delimiter ;

delimiter //
drop procedure if exists sp_createReservation;
create definer=`root`@`localhost` procedure `sp_createReservation`(
    in p_trajet_id int,
    in p_passager_id_user int,
    in p_nb_places_reserv int,
    in p_dateReservation datetime,
    in p_statut ENUM('en attente', 'confirmé', 'fini')
)
begin
    if exists(
        select 1 from reservation where trajet_id_trajet = p_trajet_id and passager_id_utilisateur = p_passager_id_user
    ) then
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Voiture déjà enregistré...';
    else
        insert into reservation(trajet_id_trajet, passager_id_utilisateur, nombre_places_reservees, date_reservation, statut) values (p_trajet_id, p_passager_id_user, p_nb_places_reserv, p_dateReservation, p_statut);
    end if;
end; //
delimiter ;

delimiter //
drop procedure if exists sp_createPaiement;
create definer=`root`@`localhost` procedure `sp_createPaiement`(
    in p_reserv_id int,
    in p_user_id int,
    in p_montant float,
    in p_dateHeure datetime,
    in p_method_paiement ENUM('CB', 'OrangeBank'),
    in p_statut ENUM('en attente', 'confirmée', 'annulée')
)
begin
    insert into paiement(reservation_id_reservation, utilisateur_id_utilisateur, montant, date_heure, methode, statut) values (p_reserv_id, p_user_id, p_montant, p_dateHeure, p_method_paiement, p_statut);
end; //
delimiter ;

delimiter //
drop procedure if exists sp_createAvis;
create definer=`root`@`localhost` procedure `sp_createAvis`(
    in p_reservation_id int,
    in p_utilisateur_idUtilisateur int,
    in p_note int,
    in p_commentaire text,
    in p_dateHeure datetime
)
begin
    insert into avis(reservation_id_reservation, utilisateur_id_utilisateur, note, commentaire, date_heure) values (p_reservation_id, p_utilisateur_idUtilisateur, p_note, p_commentaire, p_dateHeure);
end; //
delimiter ;