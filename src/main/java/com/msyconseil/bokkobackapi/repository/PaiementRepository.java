package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.PaiementModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<PaiementModel, Integer> {

    public PaiementModel save(PaiementModel paiementModel);

}
