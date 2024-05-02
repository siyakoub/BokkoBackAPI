package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.MessageModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

    @Query(value = "select * from message", nativeQuery = true)
    public List<MessageModel> findAllMessage();

    @Query(value = "SELECT * FROM message WHERE expediteur_id_utilisateur = :userId OR destinataire_id_utilisateur = :userId ORDER BY date_heure_envoi DESC, id DESC LIMIT 1", nativeQuery = true)
    public MessageModel findLastMessageForUser(@Param("userId") int userId);

    @Query(value = "select * from message where id = :id", nativeQuery = true)
    public MessageModel findMessageById(@Param("id") int id);

    @Query(value = "select * from message where expediteur_id_utilisateur = :idExpediteur and destinataire_id_utilisateur = :idDestinataire order by date_heure_envoi desc limit 1", nativeQuery = true)
    public MessageModel findLastMessageInConversation(@Param("idExpediteur") int idExpediteur, int idDestinataire);

    @Query(value = "select * from message where expediteur_id_utilisateur = :id_expediteur", nativeQuery = true)
    public List<MessageModel> findAllMessageByExpediteur(@Param("id_expediteur") int id_expediteur);

    @Query(value = "select * from message where destinataire_id_utilisateur = :id_destinataire", nativeQuery = true)
    public List<MessageModel> findAllMessageByDestinateur(@Param("id_destinataire") int id_destinataire);

    @Query(value = "select * from message where destinataire_id_utilisateur = :id_destinataire and expediteur_id_utilisateur = :id_expediteur", nativeQuery = true)
    public List<MessageModel> findAllMessageByExpediteurForDestinataire(@Param("id_destinataire") int id_destinataire, @Param("id_expediteur") int id_expediteur);

    @Query(value = "CALL sp_createMessage(:expediteur_id, :destinataire_id, :contenu, NOW(), :vu)", nativeQuery = true)
    public List<SqlStoredProcedureAnswer> sendNewMessage(@Param("expediteur_id") int expediteur, @Param("destinataire_id") int destinataire_id, @Param("contenu") String contenu, @Param("vu") boolean vu);

}
