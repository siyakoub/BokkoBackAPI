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
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conducteur_idUtilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_2"))
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
    @Column(name = "dateHeureDepart")
    private LocalDateTime dateHeureDepart;

    @Getter
    @Setter
    @Column(name = "nombrePlaces")
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
