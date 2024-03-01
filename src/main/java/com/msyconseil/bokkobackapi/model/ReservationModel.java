package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Reservation")
public class ReservationModel extends AbstractModel implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trajet_idTrajet", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_3"))
    private TrajetModel trajetModel;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passager_idUtilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_4"))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "nombrePlacesReservees")
    private int nbPlaceReserv;

    @Getter
    @Setter
    @Column(name = "date_reservation")
    private LocalDateTime dateReservation;

    @Getter
    @Setter
    @Column(name = "statut")
    private String statut;

    public ReservationModel() {
        super();
    }

    @Override
    public String toString() {
        return "Reservation [Trajet="+ trajetModel +", utilisateurPassager="+ userModel +", nbPlacesReservees="+ nbPlaceReserv +", statut="+ statut +"]";
    }

}
