package com.msyconseil.bokkobackapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "session")
public class SessionModel extends AbstractModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email",  foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserModel userModel;

    @Getter
    @Setter
    @Column(name = "token")
    private String token;

    @Getter
    @Setter
    @Column(name = "dateDebutSession")
    private LocalDateTime dateDebutSession;

    @Getter
    @Setter
    @Column(name = "dateFinSession")
    private LocalDateTime dateFinSession;

    @Getter
    @Setter
    @Column(name = "actif")
    private String actif;

    public SessionModel() {
        super();
    }

    @Override
    public String toString() {
        return "Session [utilisateur="+ userModel +", token="+ token +", dateDebutSession="+ dateDebutSession +", dateFinSession="+ dateFinSession +", actif="+ actif +"]";
    }

}
