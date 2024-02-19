package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.TrajetModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrajetRepository extends JpaRepository<TrajetModel, Integer> {

    public TrajetModel save(TrajetModel trajetModel);

}
