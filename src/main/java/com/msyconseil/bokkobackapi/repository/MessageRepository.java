package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.MessageModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
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

    @Query(value = "CALL sp_createMessage(:expediteur_id, :destinataire_id, :contenu, NOW(), :vu)", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> sendNewMessage(@Param("expediteur_id") int expediteur, @Param("destinataire_id") int destinataire_id, @Param("contenu") String contenu, @Param("vu") boolean vu);

}
