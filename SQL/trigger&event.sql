use BokkoDB;
SET GLOBAL event_scheduler = ON;

DELIMITER //

drop event if exists update_trajet_status;
CREATE EVENT update_trajet_status
    ON SCHEDULE EVERY 1 HOUR
        STARTS CURRENT_TIMESTAMP
    DO
    BEGIN
        UPDATE Trajet
        SET statut = 'en cours'
        WHERE date_heure_depart <= NOW() AND statut = 'à venir';
    END //

DELIMITER ;

DELIMITER //

DROP TRIGGER IF EXISTS after_reservation_insert;
CREATE TRIGGER after_reservation_insert
    AFTER INSERT ON Reservation
    FOR EACH ROW
BEGIN
    UPDATE Trajet
    SET nombre_places = nombre_places - NEW.nombre_places_reservees
    WHERE id = NEW.trajet_id_trajet;
END //

DELIMITER ;

DELIMITER //
DROP TRIGGER IF EXISTS after_reservation_delete;
CREATE TRIGGER after_reservation_delete
    AFTER DELETE ON Reservation
    FOR EACH ROW
BEGIN
    UPDATE Trajet
    SET nombre_places = nombre_places + OLD.nombre_places_reservees
    WHERE id = OLD.trajet_id_trajet;
END //

DELIMITER ;

DELIMITER //
drop trigger  if exists before_reservation_insert;
CREATE TRIGGER before_reservation_insert
    BEFORE INSERT ON Reservation
    FOR EACH ROW
BEGIN
    DECLARE available_places INT;

    SELECT nombre_places INTO available_places
    FROM Trajet
    WHERE id = NEW.trajet_id_trajet;

    IF available_places <= 0 OR NEW.nombre_places_reservees > available_places THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Il n’y a pas assez de places disponibles pour ce trajet.';
    END IF;
END //

DELIMITER ;

