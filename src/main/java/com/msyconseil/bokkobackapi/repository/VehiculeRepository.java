package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.VehiculeModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<VehiculeModel, Integer> {

    public VehiculeModel save(VehiculeModel vehiculeModel);

    @Query(value = "select * from Vehicule", nativeQuery = true)
    public List<VehiculeModel> findAllVehicule();

    @Query(value = "select * from vehicule where conducteur_idUtilisateur = :id", nativeQuery = true)
    public List<VehiculeModel> findAllByConducteur(@Param("id") int id);

    @Query(value = "select * from vehicule where id = :id", nativeQuery = true)
    public VehiculeModel findById(@Param("id") int id);

    @Query(value = "CALL sp_createVehicule(:conducteur_id, :marque, :modele, :couleur, :immatriculation, :annee)", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> createNewVehicule(@Param("conducteur_id") int conducteur_id, @Param("marque") String marque, @Param("modele") String modele, @Param("couleur") String couleur, @Param("immatriculation") String immatriculation, @Param("annee") int annee);

}
