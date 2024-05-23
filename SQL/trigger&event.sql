use BokkoDB;
SET GLOBAL event_scheduler = ON;

DELIMITER //

drop event if exists update_trajet_status;
CREATE EVENT update_trajet_status
    ON SCHEDULE EVERY 1 HOUR
        STARTS CURRENT_TIMESTAMP
    DO
    BEGIN
        UPDATE trajet
        SET statut = 'en cours'
        WHERE date_heure_depart <= NOW() AND statut = 'à venir';
    END //

DELIMITER ;

DELIMITER //

DROP TRIGGER IF EXISTS after_reservation_insert;
CREATE TRIGGER after_reservation_insert
    AFTER INSERT ON reservation
    FOR EACH ROW
BEGIN
    UPDATE trajet
    SET nombre_places = nombre_places - NEW.nombre_places_reservees
    WHERE id = NEW.trajet_id_trajet;
END //

DELIMITER ;

DELIMITER //
DROP TRIGGER IF EXISTS after_reservation_delete;
CREATE TRIGGER after_reservation_delete
    AFTER DELETE ON reservation
    FOR EACH ROW
BEGIN
    UPDATE trajet
    SET nombre_places = nombre_places + OLD.nombre_places_reservees
    WHERE id = OLD.trajet_id_trajet;
END //

DELIMITER ;

DELIMITER //
drop trigger  if exists before_reservation_insert;
CREATE TRIGGER before_reservation_insert
    BEFORE INSERT ON reservation
    FOR EACH ROW
BEGIN
    DECLARE available_places INT;

    SELECT nombre_places INTO available_places
    FROM trajet
    WHERE id = NEW.trajet_id_trajet;

    IF available_places <= 0 OR NEW.nombre_places_reservees > available_places THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Il n’y a pas assez de places disponibles pour ce trajet.';
    END IF;
END //

DELIMITER ;

DELIMITER //
drop event if exists update_trajet_status;
CREATE EVENT IF NOT EXISTS update_trajet_status
    ON SCHEDULE EVERY 1 HOUR
    DO
    BEGIN
        -- Met à jour les trajets qui sont en cours et dont l'heure de départ est passée d'une heure
        UPDATE trajet
        SET statut = 'terminé'
        WHERE statut = 'en cours' AND TIMESTAMPDIFF(HOUR, date_heure_depart, NOW()) >= 1;

        -- Met à jour les réservations associées aux trajets terminés
        UPDATE reservation r
            JOIN trajet t ON r.trajet_id_trajet = t.id
        SET r.statut = 'fini'
        WHERE t.statut = 'terminé' AND r.statut <> 'fini';
    END //

DELIMITER ;



