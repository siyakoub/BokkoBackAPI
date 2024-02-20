package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer>{

    @Query(value = "select * from Utilisateur", nativeQuery = true)
    public List<UserModel> findAllUsers();

    @Query(value = "select * from Utilisateur where actif = 'A'", nativeQuery = true)
    public List<UserModel> findAllByActif();

    @Query(value = "select * from Utilisateur where actif = 'I'", nativeQuery = true)
    public List<UserModel> findAllByInactif();

    @Query(value = "select * from Utilisateur where actif = 'B'", nativeQuery = true)
    public List<UserModel> findAllByBloqued();

    @Query(value = "select * from Utilisateur where email = :email", nativeQuery = true)
    public UserModel findByEmail(@Param("email") String email);

    @Query(value = "select * from Utilisateur where email = :email and actif = 'A'", nativeQuery = true)
    public UserModel findByEmailAndActif(@Param("email") String email);

    @Query(value = "select * from Utilisateur where email = :email and actif = 'B'", nativeQuery = true)
    public UserModel findByEmailAndBlocked(@Param("email") String email);

    @Query(value = "select * from Utiilisateur where email = :email and actif = 'I'", nativeQuery = true)
    public UserModel findByEmailAndInactif(@Param("email") String email);

    @Query(value = "select * from Utilisateur where id = :id", nativeQuery = true)
    public UserModel findAllById(@Param("id") int id);


}
