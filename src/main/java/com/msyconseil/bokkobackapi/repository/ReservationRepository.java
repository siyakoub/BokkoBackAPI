package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.ReservationModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Integer> {

    public ReservationModel save(ReservationModel reservationModel);

    @Query(value = "select * from Reservation", nativeQuery = true)
    public List<ReservationModel> findAllReservation();

    @Query(value = "select * from Reservation where id = :id", nativeQuery = true)
    public ReservationModel findById(@Param("id") int id);

    @Query(value = "select * from Reservation where passager_id_utilisateur = :idPassager and statut = 'en attente' ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationInProgressByPassager(@Param("idPassager") int idPassager);

    @Query(value = "select * from Reservation where passager_id_utilisateur = :idPassager and statut = 'confirmé' ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationConfirmedByPassager(@Param("idPassager") int idPassager);

    @Query(value = "SELECT * FROM Reservation WHERE passager_id_utilisateur = :idPassager AND statut = 'fini' ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationFinishedByPassager(@Param("idPassager") int idPassager);

    @Query(value = "SELECT * from Reservation where trajet_id_trajet = :idTrajet and statut = 'en attente' ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationInProgressByTrajet(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where trajet_id_trajet = :idTrajet and statut = 'confirmé' ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationConfirmedByTrajet(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where trajet_id_trajet = :idTrajet and statut = 'confirmé' ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationFinishedByTrajet(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where passager_id_utilisateur = :idPassager ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationByPassager(@Param("idPassager") int idPassager);

    @Query(value = "select * from Reservation where trajet_id_trajet = :idTrajet ORDER BY date_reservation DESC LIMIT 1", nativeQuery = true)
    public ReservationModel findLastReservationByTrajet(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where trajet_id_trajet = :id_trajet", nativeQuery = true)
    public List<ReservationModel> findByAllByTrajet(@Param("id_trajet") int id_trajet);

    @Query(value = "select * from Reservation where passager_id_utilisateur = :id_passager", nativeQuery = true)
    public List<ReservationModel> findAllReservationByPassager(@Param("id_passager") int id_passager);

    @Query(value = "select * from Reservation where passager_id_utilisateur = :id_passager and trajet_id_trajet = :id_trajet", nativeQuery = true)
    public ReservationModel findReservationByPassagerAndTrajet(@Param("id_passager") int id_passager, @Param("id_trajet") int id_trajet);

    @Query(value = "select * from Reservation where statut = 'en attente'", nativeQuery = true)
    public List<ReservationModel> findReservationByStatutInProgress();

    @Query(value = "select * from Reservation where statut = 'en attente' and trajet_id_trajet = :idTrajet", nativeQuery = true)
    public List<ReservationModel> findAllReservationByTrajetInProgress(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where statut = 'confirmé' and trajet_id_trajet = :idTrajet", nativeQuery = true)
    public List<ReservationModel> findAllReservationByTrajetConfirmed(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where statut = 'fini' and trajet_id_trajet = :idTrajet", nativeQuery = true)
    public List<ReservationModel> findAllReservationByTrajetFinished(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where statut = 'Annulé' and trajet_id_trajet = :idTrajet", nativeQuery = true)
    public List<ReservationModel> findAllReservationByTrajetCanceled(@Param("idTrajet") int idTrajet);

    @Query(value = "select * from Reservation where statut = 'confirmé'", nativeQuery = true)
    public List<ReservationModel> findReservationByStatutInConfirmation();

    @Query(value = "select * from Reservation where statut = 'fini'", nativeQuery = true)
    public List<ReservationModel> findReservationByStatutFinished();

    @Query(value = "CALL sp_createReservation(:trajet_id, :passager_id, :nb_places_reserv, :statut)", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> createNewReservation(@Param("trajet_id") int trajet_id, @Param("passager_id") int passager_id, @Param("nb_places_reserv") int nb_places_reserv, @Param("statut") String statut);

}
