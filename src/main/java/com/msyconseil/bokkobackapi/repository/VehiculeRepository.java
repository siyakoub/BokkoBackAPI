package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.VehiculeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<VehiculeModel, Integer> {

    public VehiculeModel save(VehiculeModel vehiculeModel);



}
