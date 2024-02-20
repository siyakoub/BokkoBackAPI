package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.VehiculeModel;
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

}
