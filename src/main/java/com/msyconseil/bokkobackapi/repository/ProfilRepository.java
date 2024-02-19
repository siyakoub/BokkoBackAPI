package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.ProfilModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilRepository extends JpaRepository<ProfilModel, Integer> {

    public ProfilModel save(ProfilModel profilModel);

}
