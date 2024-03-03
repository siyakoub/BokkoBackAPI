package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.AvisModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvisRepository extends JpaRepository<AvisModel, Integer> {

    @Query(value = "select * from Avis", nativeQuery = true)
    public List<AvisModel> findAllAvis();

    @Query(value = "select * from Avis where utilisateur_idUtilisateur = :idUser", nativeQuery = true)
    public List<AvisModel> findAllAvisByUser(@Param("idUser") int idUser);

    @Query(value = "select * from Avis where utilisateur_idUtilisateur = :idUser order by id desc limit 1", nativeQuery = true)
    public AvisModel findLastAvisByUser(@Param("idUser") int idUser);

    @Query(value = "select * from Avis where reservation_idReservation = :idReservation order by id desc limit 1", nativeQuery = true)
    public AvisModel findLastAvisByReservation(@Param("idReservation") int idReservation);

    @Query(value = "select * from Avis where reservation_idReservation = :idReservation and utilisateur_idUtilisateur = :idUser order by id desc limit 1", nativeQuery = true)
    public AvisModel findLastAvisByReservationAndUser(@Param("idReservation") int idReservation, @Param("idUser") int idUser);

    @Query(value = "select * from Avis where reservation_idReservation = :idReservation and utilisateur_idUtilisateur = :idUser", nativeQuery = true)
    public List<AvisModel> findAllByReservationAndUser(@Param("idReservation") int idReservation, @Param("idUser") int idUser);

    @Query(value = "select * from Avis where id = :id", nativeQuery = true)
    public AvisModel findAvisById(@Param("id") int id);

    @Query(value = "select * from Avis where reservation_idReservation = :id_reservation", nativeQuery = true)
    public List<AvisModel> findAllAvisByReservation(@Param("id_reservation") int id_reservation);

    @Query(value = "CALL sp_createAvis(:reservation_id, :user_id, :note, :commentaire, NOW())", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> createNewAvisCustomer(@Param("reservation_id") int reservation_id, @Param("user_id") int user_id, @Param("note") int note, @Param("commentaire") String commentaire);

}
