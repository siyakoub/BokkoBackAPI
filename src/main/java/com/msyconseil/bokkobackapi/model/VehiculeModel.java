package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Vehicule")
public class VehiculeModel extends AbstractModel implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "conducteur_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_5"))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "marque")
    private String marque;

    @Getter
    @Setter
    @Column(name = "modele")
    private String modele;

    @Getter
    @Setter
    @Column(name = "couleur")
    private String couleur;

    @Getter
    @Setter
    @Column(name = "immatriculation")
    private String immatriculation;

    @Getter
    @Setter
    @Column(name = "annee")
    private int annee;

    public VehiculeModel() {
        super();
    }

    @Override
    public String toString(){
        return "Vehicule [utilisateurConducteur="+ userModel +", marque="+ marque +", modele="+ modele +", couleur="+ couleur +", immatriculation="+ immatriculation +", annee="+ annee +"]";
    }

}
