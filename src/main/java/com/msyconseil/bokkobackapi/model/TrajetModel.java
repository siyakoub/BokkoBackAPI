package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Trajet")
public class TrajetModel extends AbstractModel implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conducteur_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "trajet_ibfk_1"))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "depart")
    private String depart;

    @Getter
    @Setter
    @Column(name = "arrivee")
    private String arrivee;

    @Getter
    @Setter
    @Column(name = "date_heure_depart")
    private LocalDateTime dateHeureDepart;

    @Getter
    @Setter
    @Column(name = "nombre_places")
    private int nbPlaces;

    @Getter
    @Setter
    @Column(name = "prix")
    private float prix;

    @Getter
    @Setter
    @Column(name = "statut")
    private String statut;

    public TrajetModel() {
        super();
    }

    @Override
    public String toString() {
        return "Trajet [utilisateurConducteur="+ userModel +", depart="+ depart +", arrivee="+ arrivee +", dateHeureDepart="+ dateHeureDepart +", nbPlaces="+ nbPlaces +", prix="+ prix +", statut="+ statut +"]";
    }

}
