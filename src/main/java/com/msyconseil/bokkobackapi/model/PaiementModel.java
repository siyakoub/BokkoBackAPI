package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Paiement")
public class PaiementModel extends AbstractModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_idReservation", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_6"))
    private ReservationModel reservationModel;

    @Getter
    @Setter
    @Column(name = "montant")
    private float montant;

    @Getter
    @Setter
    @Column(name = "dateHeure")
    private LocalDateTime dateHeurePaiement;

    @Getter
    @Setter
    @Column(name = "methode")
    private String methode;

    @Getter
    @Setter
    @Column(name = "statut")
    private String statut;

    public PaiementModel() {
        super();
    }

    @Override
    public String toString() {
        return "Paiement [reservation="+ reservationModel +", montant="+ montant +", dateHeure="+ dateHeurePaiement +", methodePaiement="+ methode +", statut="+ statut +"]";
    }

}
