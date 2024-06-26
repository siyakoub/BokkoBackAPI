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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id_reservation", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "paiement_ibfk_1"))
    private ReservationModel reservationModel;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "utilisateur_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "paiement_ibfk_2"))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "montant")
    private float montant;

    @Getter
    @Setter
    @Column(name = "date_heure")
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
