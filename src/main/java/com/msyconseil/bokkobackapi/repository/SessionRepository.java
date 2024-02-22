package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.SessionModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<SessionModel, Integer> {

    @Query(value = "select * from session where token = :token", nativeQuery = true)
    public SessionModel findByToken(@Param("token") String token);

    @Query(value = "select * from session where actif = 'E'", nativeQuery = true)
    public List<SessionModel> findAllActif();

    @Query(value = "select * from session where actif = 'T'", nativeQuery = true)
    public List<SessionModel> findAllByInactif();

    @Query(value = "select * from session", nativeQuery = true)
    public List<SessionModel> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE session set actif='T', dateFinSession = NOW() where token = :token", nativeQuery = true)
    public void delete(@Param("token") String token);

}
