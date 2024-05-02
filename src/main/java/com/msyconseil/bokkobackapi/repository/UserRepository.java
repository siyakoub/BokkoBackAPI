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

    @Query(value = "select * from utilisateur", nativeQuery = true)
    public List<UserModel> findAllUsers();

    @Query(value = "select * from utilisateur where statut = 'A'", nativeQuery = true)
    public List<UserModel> findAllByActif();

    @Query(value = "select * from utilisateur where statut = 'I'", nativeQuery = true)
    public List<UserModel> findAllByInactif();

    @Query(value = "select * from utilisateur where statut = 'B'", nativeQuery = true)
    public List<UserModel> findAllByBloqued();

    @Query(value = "select * from utilisateur where email = :email", nativeQuery = true)
    public UserModel findByEmail(@Param("email") String email);

    @Query(value = "select * from utilisateur where email = :email and statut = 'A'", nativeQuery = true)
    public UserModel findByEmailAndActive(@Param("email") String email);

    @Query(value = "select * from utilisateur where email = :email and statut = 'B'", nativeQuery = true)
    public UserModel findByEmailAndBlocked(@Param("email") String email);

    @Query(value = "select * from utilisateur where email = :email and statut = 'I'", nativeQuery = true)
    public UserModel findByEmailAndInactif(@Param("email") String email);

    @Query(value = "select * from utilisateur where id = :id", nativeQuery = true)
    public UserModel findAllById(@Param("id") int id);


}
