package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.ProfilModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProfilRepository extends JpaRepository<ProfilModel, Integer> {

    public ProfilModel save(ProfilModel profilModel);

    @Query(value = "select * from Profil", nativeQuery = true)
    public List<ProfilModel> findAllProfils();

    @Query(value = "select * from Profil where utilisateur_id_utilisateur = :id_user", nativeQuery = true)
    public ProfilModel findProfilByUser(@Param("id_user") int id_user);

    @Query(value = "select * from Profil where id = :id", nativeQuery = true)
    public ProfilModel findProfilById(@Param("id") int id);

    @Query(value = "CALL sp_createProfil(:name, :firstName, :email, :password, :phoneNumber, :dateInscription , :statut, :bio, :picture)", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> register(@Param("name") String name, @Param("firstName") String firstName, @Param("email") String email, @Param("password") String password, @Param("phoneNumber") String phoneNumber, @Param("dateInscription") LocalDateTime dateInscription, @Param("statut") String statut, @Param("bio") String bio, @Param("picture") String picture);

    @Modifying
    @Query(value = "DELETE from Profil where utilisateur_id_utilisateur = :id_user", nativeQuery = true)
    public void deleteProfilByUserId(@Param("id_user") int id_user);

}
