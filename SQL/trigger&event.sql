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

