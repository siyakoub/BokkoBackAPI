package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.ProfilModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfilRepository extends JpaRepository<ProfilModel, Integer> {

    public ProfilModel save(ProfilModel profilModel);

    @Query(value = "select * from Profil", nativeQuery = true)
    public List<ProfilModel> findAllProfils();

    @Query(value = "select * from Profil where utilisateur_idUtilisateur = :id_user", nativeQuery = true)
    public ProfilModel findProfilByUser(@Param("id_user") int id_user);

    @Query(value = "select * from Profil where id = :id", nativeQuery = true)
    public ProfilModel findProfilById(@Param("id") int id);

}
