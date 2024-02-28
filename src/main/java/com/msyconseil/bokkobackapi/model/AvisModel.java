package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Avis")
public class AvisModel extends AbstractModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_idReservation", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_7"))
    private ReservationModel reservationModel;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_idUtilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_10"))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "note")
    private int note;

    @Getter
    @Setter
    @Column(name = "commentaire")
    private String commentaire;

    @Getter
    @Setter
    @Column(name = "dateHeure")
    private LocalDateTime dateHeureAvis;

    public AvisModel() {
        super();
    }

    @Override
    public String toString() {
        return "Avis [reservation="+ reservationModel +", note="+ note +", commentaire="+ commentaire +", dateHeureAvis="+ dateHeureAvis +"]";
    }

}
