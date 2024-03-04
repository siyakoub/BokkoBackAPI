package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Profil")
public class ProfilModel extends AbstractModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id_utilisateur", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "bokko_ibfk_1"))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "bio")
    private String bio;

    @Getter
    @Setter
    @Column(name = "photo")
    private String pictureProfil;

    public ProfilModel() { super(); }

    @Override
    public String toString() {
        return "Profil [Biographie="+ bio +", Picture="+ pictureProfil +"]";
    }


}
