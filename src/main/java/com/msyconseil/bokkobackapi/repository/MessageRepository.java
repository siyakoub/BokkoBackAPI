package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

    @Query(value = "select * from Message", nativeQuery = true)
    public List<MessageModel> findAllMessage();

    @Query(value = "select * from Message where id = :id", nativeQuery = true)
    public List<MessageModel> findMessageById(@Param("id") int id);

    @Query(value = "select * from Message where expediteur_idUtilisateur = :id_expediteur", nativeQuery = true)
    public List<MessageModel> findAllMessageByExpediteur(@Param("id_expediteur") int id_expediteur);

    @Query(value = "select * from Message where destinataire_idUtilisateur = :id_destinataire", nativeQuery = true)
    public List<MessageModel> findAllMessageByDestinateur(@Param("id_destinataire") int id_destinataire);

    @Query(value = "select * from Message where destinataire_idUtilisateur = :id_destinataire and expediteur_idUtilisateur = :id_expediteur", nativeQuery = true)
    public List<MessageModel> findAllMessageByExpediteurForDestinataire(@Param("id_destinataire") int id_destinataire, @Param("id_expediteur") int id_expediteur);

}
