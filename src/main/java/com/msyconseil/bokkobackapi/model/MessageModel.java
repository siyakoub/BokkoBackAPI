package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Message")
public class MessageModel extends AbstractModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expediteur_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_8"))
    private UserModel expediteur;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destinataire_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_9"))
    private UserModel destinataire;

    @Getter
    @Setter
    @Column(name = "contenu")
    private String contenu;

    @Getter
    @Setter
    @Column(name = "dateHeureEnvoi")
    private LocalDateTime dateHeureEnvoi;

    @Getter
    @Setter
    @Column(name = "lu")
    private boolean lu;

    public MessageModel() {
        super();
    }

    @Override
    public String toString() {
        return "Message [expediteur="+ expediteur +", destinataire="+ destinataire +", contenu="+ contenu +", dateHeureEnvoi="+ dateHeureEnvoi +", lu="+ lu +"]";
    }



}
