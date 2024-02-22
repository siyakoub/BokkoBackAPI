package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.PaiementModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaiementRepository extends JpaRepository<PaiementModel, Integer> {

    @Query(value = "select * from Paiement", nativeQuery = true)
    public List<PaiementModel> findAllPaiement();

    @Query(value = "select * from Paiement where id = :id", nativeQuery = true)
    public PaiementModel findById(@Param("id") int id);

    @Query(value = "select * from Paiement where reservation_idReservation = :id_reservation", nativeQuery = true)
    public PaiementModel findPaiementByReservation(@Param("id_reservation") int id);

    @Query(value = "select * from Paiement where statut = 'en attente'", nativeQuery = true)
    public List<PaiementModel> findAllPaiementByStatutInProgress();

    @Query(value = "select * from Paiement where statut = 'confirmée'", nativeQuery = true)
    public List<PaiementModel> findAllPaiementByStatutConfirmed();

    @Query(value = "select * from Paiement where statut = 'annulée'", nativeQuery = true)
    public List<PaiementModel> findAllPaiementByStatutCanceled();

    @Query(value = "CALL sp_createPaiement(:reservation_id, :montant, NOW(), :methodPaiement, :statut)", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> createNewPaiement(@Param("reservation_id") int reservation_id, @Param("montant") float montant, @Param("methodPaiement") String methodPaiement, @Param("statut") String statut);


}
