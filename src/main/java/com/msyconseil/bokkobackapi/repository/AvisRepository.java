package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.AvisModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<AvisModel, Integer> {

    public AvisModel save(AvisModel avisModel);

}
