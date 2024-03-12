package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "Vehicule")
public class VehiculeModel extends AbstractModel implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conducteur_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_5"))
    private UserModel userModel;

    @Setter
    @Column(name = "marque")
    private String marque;

    @Setter
    @Column(name = "modele")
    private String modele;

    @Setter
    @Column(name = "couleur")
    private String couleur;

    @Setter
    @Column(name = "immatriculation")
    private String immatriculation;

    @Setter
    @Column(name = "annee")
    private int annee;

    @Setter
    @Column(name = "used")
    private int used;

    public VehiculeModel() {
        super();
    }

    @Override
    public String toString(){
        return "Vehicule [utilisateurConducteur="+ userModel +", marque="+ marque +", modele="+ modele +", couleur="+ couleur +", immatriculation="+ immatriculation +", annee="+ annee +", used="+ used +"]";
    }

}
