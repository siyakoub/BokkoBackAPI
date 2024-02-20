package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.AvisModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvisRepository extends JpaRepository<AvisModel, Integer> {

    @Query(value = "select * from Avis", nativeQuery = true)
    public List<AvisModel> findAllAvis();

    @Query(value = "select * from Avis where id = :id", nativeQuery = true)
    public AvisModel findAvisById(@Param("id") int id);

    @Query(value = "select * from Avis where reservation_idReservation = :id_reservation", nativeQuery = true)
    public List<AvisModel> findAllAvisByReservation(@Param("id_reservation") int id_reservation);

}
