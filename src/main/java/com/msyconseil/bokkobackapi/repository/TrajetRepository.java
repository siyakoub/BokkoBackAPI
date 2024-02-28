package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.TrajetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TrajetRepository extends JpaRepository<TrajetModel, Integer> {

    public TrajetModel save(TrajetModel trajetModel);

    @Query(value = "select * from Trajet", nativeQuery = true)
    public List<TrajetModel> findAllTrajet();

    @Query(value = "select * from Trajet where conducteur_idUtilisateur = :idConducteur ORDER BY dateHeureDepart DESC LIMIT 1", nativeQuery = true)
    public TrajetModel findLastTrajetByConducteur(@Param("idConducteur") int idConducteur);

    @Query(value = "select * from trajet where conducteur_idUtilisateur = :id_conducteur", nativeQuery = true)
    public List<TrajetModel> findAllByDriver(@Param("id_conducteur") int id_conducteur);

    @Query(value = "select * from trajet where id = :id", nativeQuery = true)
    public TrajetModel findById(@Param("id") int id);

    @Query(value = "select * from trajet where statut = 'à venir'", nativeQuery = true)
    public List<TrajetModel> findAllByStatutToBecome();

    @Query(value = "select * from trajet where statut = 'en cours'", nativeQuery = true)
    public List<TrajetModel> findAllByStatutInProgress();

    @Query(value = "select * from trajet where statut = 'terminé'", nativeQuery = true)
    public List<TrajetModel> findAllByStatutFinished();

    @Query(value = "select * from trajet where statut = 'annulée'", nativeQuery = true)
    public List<TrajetModel> findAllByStatutCanceled();

    @Query(value = "select * from trajet where statut = 'à venir' and conducteur_idUtilisateur = :id_conducteur", nativeQuery = true)
    public List<TrajetModel> findAllByStatutToBecomeForDriver(@Param("id_conducteur") int id_conducteur);

    @Query(value = "select * from trajet where statut = 'en cours' and conducteur_idUtilisateur = :id_conducteur", nativeQuery = true)
    public List<TrajetModel> findAllByStatutInProgressForDriver(@Param("id_conducteur") int id_conducteur);

    @Query(value = "select * from trajet where statut= 'terminé' and conducteur_idUtilisateur = :id_conducteur", nativeQuery = true)
    public List<TrajetModel> findAllByStatutFinishedForDriver(@Param("id_conducteur") int id_conducteur);

    @Query(value = "select * from trajet where statut= 'annulée' and conducteur_idUtilisateur = :id_conducteur", nativeQuery = true)
    public List<TrajetModel> findAllByStatutCanceledForDriver(@Param("id_conducteur") int id_conducteur);



}
